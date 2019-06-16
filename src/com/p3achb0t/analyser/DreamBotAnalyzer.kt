package com.p3achb0t.analyser

import com.p3achb0t.class_generation.isBaseType
import com.p3achb0t.class_generation.isFieldNameUnique
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.tree.ClassNode
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.util.jar.JarFile
import kotlin.collections.set

// Download file from http://cdn.dreambot.org/hooks.txt
// Parse file to identify the classes
// parse file to identify the fields

class DreamBotAnalyzer{
    private var webAddress: String = "http://cdn.dreambot.org/hooks.txt"
    val hookDir = "\\hooks\\"

    var hookFile = ""

    val analyzers = mutableMapOf<String, RuneLiteJSONClasses.ClassDefinition>()
    val classRefObs = mutableMapOf<String, RuneLiteJSONClasses.ClassDefinition>()
    fun getDreamBotHooks():String{

        //Download gamepack
        println("Getting Hooks from Dreambot")
        val url = URL(webAddress)
        val readableByteChannel = Channels.newChannel(url.openStream())
        val path = System.getProperty("user.dir")
        val fileOutStream = FileOutputStream(path + hookDir + "hooks.txt")
        val fileChannel = fileOutStream.channel
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE)
        println("downloaded!")
        hookFile = path + hookDir  + "hooks.txt"
        return path + hookDir  + "hooks.txt"

    }

    fun parseHooks(){
        // Init all classes we care about


        // Open file
        // Parse file and update classes
        // Update fields
        var currentClass = ""
        File(this.hookFile).forEachLine {
            //Class line "@Client identified as: client"
            if(it.contains("@")){
                val list = it.split(" ")
                val className = list[0].replace("@","")
                val obsName = list.last()
                currentClass = className
                val newClazz = RuneLiteJSONClasses.ClassDefinition()

                newClazz.name = obsName
                newClazz._class = className
                analyzers[className] = newClazz

//                (analyzers[className] as RSClasses).obsName = obsName
//                (analyzers[className] as RSClasses).className = className
                classRefObs[obsName] = analyzers[className]!!
                println(className)
            }
            //Field line: "Client.accountStatus cu ae -2093036075"
            else if(it.contains(currentClass)){
                val fieldLine = it.replace("$currentClass.", "")
                val splitField = fieldLine.split(" ")
                val field = RuneLiteJSONClasses.FieldDefinition()
                field.field = splitField[0]
                field.owner = splitField[1]
                if(splitField.size>2)
                    field.name = splitField[2]
                if (splitField.size > 3) {
                    if (splitField[3].contains("L")) {
                        field.decoderType = RuneLiteJSONClasses.DecoderType.LONG
                    }
                    field.decoder = splitField[3].replace("L", "").toLong()
                }
                analyzers[currentClass]?.fields?.add(field)
//                classRefObs[analyzers[currentClass]?.name]?.fields?.add(field)

                println("\t$field")
            }
        }
    }

    private fun genFunction(
        field: RuneLiteJSONClasses.FieldDefinition,
        clazz: MutableMap.MutableEntry<String, RuneLiteJSONClasses.ClassDefinition>,
        classRefObs: MutableMap<String, RuneLiteJSONClasses.ClassDefinition>
    ) {
        val fieldCount = isFieldNameUnique(classRefObs[clazz.value._super], field.field, classRefObs)
//        println("\tChecking Unique for ${field.field}. Super: ${clazz.value._super} Count: $fieldCount")
        if (fieldCount > 0) {
            val functionName = "${clazz.value._class}_${field.field.replace("get","").decapitalize()}"
//            println("Not unique Name $functionName")
            field.field = "get${functionName.capitalize()}"
        }
    }


    fun parseJar(jar: JarFile){
        // We are going to look at the Jar and find the Class Nodes so can get more data

        val classNodeRefs = mutableMapOf<String, ClassNode>()
        val enumeration = jar.entries()
        while(enumeration.hasMoreElements()){
            val entry = enumeration.nextElement()
            if(entry.name.endsWith(".class")){
                val classReader = ClassReader(jar.getInputStream(entry))
                val classNode = ClassNode()
                classReader.accept(classNode, ClassReader.SKIP_DEBUG)// Missing | ClassReader.SKIP_FRAMES
                classNodeRefs[classNode.name] = classNode
                if(classNode.name in classRefObs) {
                    classRefObs[classNode.name]?._super = classNode.superName
                    classRefObs[classNode.name]?.access = classNode.access
                    for (field in classNode.fields) {

                        val foundField = classRefObs[classNode.name]?.fields?.find { it.name == field.name }
                        if (foundField != null) {
                            foundField.descriptor = field.desc
                            foundField.access = field.access

                            println("\t$foundField")
                        }
                    }

//                    classRefObs[classNode.name]?.node = classNode
//                    analyzers[this.classRefObs[classNode.name]!!::class.java.simpleName]?.node = classNode
                }
            }
        }

        //Update desc and access of all fileds in classRefObs and analyzers
        //Loop over each class, then field found in classRef
        classRefObs.forEach { className, classObj ->
            classObj.fields.forEach { field ->
                if(field.owner in classNodeRefs){
                    println("Looking for ${field.field} in ${field.owner}(${classRefObs[field.owner]?._class}) obs name: ${field.name} desc: ${field.descriptor} ")
                    classNodeRefs[field.owner]?.fields?.forEach {
                        if (field.name == it.name) {
                            field.descriptor = it.desc
                            field.access = it.access
                            println("\t Update desc:${it.desc}")

                        }
                    }
                }
            }
        }

        //Update the fields to make sure they are unique in the class heirarchy
        for (clazz in analyzers) {
            println("Class: ${clazz.value._class}")
            for (field in clazz.value.fields) {
                val arrayCount = field.descriptor.count { it == '[' }
                var updatedDescriptor = field.descriptor
                //Trim array brackets
                if (arrayCount > 0) {
                    updatedDescriptor = field.descriptor.substring(arrayCount, field.descriptor.length)
                }
                //Trim L; for long types
                if (field.descriptor.contains(";")) {
                    updatedDescriptor = updatedDescriptor.substring(1, updatedDescriptor.length - 1)

                }
                //Simplification, just set the base with the getFunction and then check if it needs to be updated
                if(!field.field.contains("get")){
                    field.field = "get${field.field.capitalize()}"
                }
                if (updatedDescriptor in classRefObs) {
                    genFunction(field, clazz, classRefObs)
                } else {
                    if (isBaseType(updatedDescriptor)) {
                        if (clazz.value._super in classRefObs) {
                            genFunction(field, clazz, classRefObs)
                        }
                    }
                }
            }
        }

        val newPackage = "hook_interfaces"
        val folder = "./\\src/com/p3achb0t/$newPackage/"
        val _package = "com.p3achb0t.$newPackage"

//        createInterfaces(folder, _package, analyzers, classRefObs)
//        createJavaInterfaces("./src/org/bot/client/wrapper/","org.bot.client.wrapper",analyzers, classRefObs)
    }
//TODO - Update when we need to generate super graph
//    fun getSuperName(classNode: ClassNode):String{
//        return if(classNode.superName == "java/lang/Object"){
//            " -> "+ classNode.superName
//        } else{
//            return if(classNode.superName in classRefObs) {
//                " -> " + classRefObs[classNode.superName]!!::class.java.simpleName + classRefObs[classNode.superName]?.node?.let {
//                    getSuperName(
//                        it
//                    )
//                }
//            }else{
//                " -> " + classNode.superName
//            }
//
//        }
//    }
//    fun generateSuperGraph(){
//        for(classNodeEntry in classRefObs){
//            val classNode = classNodeEntry.value.node
//            val classNodeName = classNodeEntry.value::class.java.simpleName
//
//            val superName = getSuperName(classNode)
//            println("$classNodeName $superName")
////            val list = superName.split("->")
////            println("\t$classNodeName implements ${list[1]}")
////            createInterfaceForInjection(classNodeEntry.value::class.java, list[1])
//        }
//    }
}