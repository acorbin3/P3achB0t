package com.p3achb0t.analyser.runestar

import com.google.gson.Gson
import com.p3achb0t.Main
import com.p3achb0t.analyser.class_generation.createRunestarInterfaces
import com.p3achb0t.analyser.class_generation.isBaseType
import com.p3achb0t.analyser.class_generation.isFieldNameUnique
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.lang.System.exit
import java.util.jar.JarFile

class RuneStarAnalyzer {
    val analyzers = mutableMapOf<String, ClassHook>()   // key is the obfuscated class name
    val classRefObs = mutableMapOf<String, ClassHook>() // Key is the identified class name
    fun loadHooks(): String {
        val hookDir = "/hooks/"
        val path = System.getProperty("user.dir")
        val hookFileName = "hooks.json"
        var json = ""

        //Depending on if we are running within IntelliJ or from Jar the hooks file might be in a different location
        json = if( File("./$hookDir/$hookFileName").exists()) {
            File("./$hookDir/$hookFileName").readText()
        }else{
            Main.javaClass.getResourceAsStream("/$hookFileName").bufferedReader().readLines().forEach { json+= it + "\n" }
            json
        }
//        val json = file.readText() // your json value here
        val topic = Gson().fromJson(json, Array<ClassHook>::class.java)

        for (clazz in topic) {

            analyzers[clazz.`class`] = clazz
            classRefObs[clazz.name] = clazz
        }

        val folder = "./src/com/p3achb0t/api/interfaces2/"
        val _package = "com.p3achb0t.api.interfaces"

        //get client debug text
        for(clazz in analyzers){
            if(clazz.value.`class` == "Client"){
                for (field in clazz.value.fields) {
                    var updatedDescriptor = field.descriptor
                    if (isBaseType(updatedDescriptor)) {
                        //Check to ensure that there are no fields with the same name in the super class
                        //Filter all methods that dont have a renamed function
                        //&& "get__+${field.owner}_${field.name}" == field.getterMethod
                        "debugText.add(DebugText(\"hasFocus? x: \${ctx.client.getHasFocus()}\"))"

                        if(updatedDescriptor == "I" && field.getterMethod.contains("get__")){
                            println("debugText.add(DebugText(\"${field.getterMethod.padEnd(15)}: \${ctx.client.${field.getterMethod}()}\"))")
                        }

                        //println("Strings")
//                        if(updatedDescriptor.contains("String") && field.getterMethod.contains("get__")){
//                            println("debugText.add(DebugText(\"${field.getterMethod.padEnd(15)}: \${ctx.client.${field.getterMethod}()}\"))")
//                        }
                    }
                }
            }
        }

//            createRunestarInterfaces(folder, _package, analyzers, classRefObs)
//        exit(0)
        return "./$hookDir/$hookFileName"
    }

    private fun genFunction(
        field: FieldHook,
        clazz: MutableMap.MutableEntry<String, ClassHook>,
        classRefObs: MutableMap<String, ClassHook>
    ) {
        val fieldWithoutGet = field.field.replace("get", "").decapitalize()
        //Have to check both with and without Get just to insure that the field hasnt chaned for the parent classes
        var fieldCount = isFieldNameUnique(classRefObs[clazz.value.`super`], fieldWithoutGet, classRefObs)
        fieldCount += isFieldNameUnique(classRefObs[clazz.value.`super`], field.field, classRefObs)
//        println("\tChecking Unique for ${field.field},$fieldWithoutGet. Super: ${clazz.value._super} Count: $fieldCount")
        if (fieldCount > 0) {
            val functionName = "${clazz.value.`class`}_$fieldWithoutGet"
//            println("Not unique Name $functionName")
            field.field = "get${functionName.capitalize()}"
        }
    }


    fun parseJar(jar: JarFile) {
        // We are going to look at the Jar and find the Class Nodes so can get more data
        // to ensure we have unique field names, calss names in the hierarchy structure.
        // Also add in the "get"'s as part of the interfaces

        val classNodeRefs = mutableMapOf<String, ClassNode>()
        val enumeration = jar.entries()
        while (enumeration.hasMoreElements()) {
            val entry = enumeration.nextElement()
            if (entry.name.endsWith(".class")) {
                val classReader = ClassReader(jar.getInputStream(entry))
                val classNode = ClassNode()
                classReader.accept(classNode, ClassReader.SKIP_DEBUG)// Missing | ClassReader.SKIP_FRAMES
                classNodeRefs[classNode.name] = classNode
                if (classNode.name in classRefObs) {
                    classRefObs[classNode.name]?.`super` = classNode.superName
                    classRefObs[classNode.name]?.access = classNode.access
                    for (field in classNode.fields) {

                        val foundField = classRefObs[classNode.name]?.fields?.find { it.name == field.name }
                        if (foundField != null) {
                            foundField.descriptor = field.desc
                            foundField.access = field.access

                            //println("\t$foundField")
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
                if (field.owner in classNodeRefs) {
                    //println("Looking for ${field.field} in ${field.owner}(${classRefObs[field.owner]?.`class`}) obs name: ${field.name} desc: ${field.descriptor} ")
                    classNodeRefs[field.owner]?.fields?.forEach {
                        if (field.name == it.name) {
                            field.descriptor = it.desc
                            field.access = it.access
                            //println("\t Update desc:${it.desc}")

                        }
                    }
                }
            }
        }

        //Update the fields to make sure they are unique in the class hierarchy
        for (clazz in analyzers) {
            //println("Class: ${clazz.value.`class`}")
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
                if (!field.field.contains("get")) {
                    field.field = "get${field.field.capitalize()}"
                } else if (field.field.length >= 3 && !field.field.substring(0, 2).contains("get")) {
                    field.field = "get${field.field.capitalize()}"
                }
                if (updatedDescriptor in classRefObs) {
                    genFunction(field, clazz, classRefObs)
                } else {
                    if (isBaseType(updatedDescriptor)) {
                        if (clazz.value.`class` in classRefObs) {
                            genFunction(field, clazz, classRefObs)
                        }
                    }
                }
            }
        }

        val newPackage = "hook_interfaces"
        val folder = "./src/com/p3achb0t/$newPackage/"
        val _package = "com.p3achb0t.$newPackage"



//        createInterfaces(folder, _package, analyzers, classRefObs)
//        createJavaInterfaces("./src/org/bot/applet/wrapper/","org.bot.applet.wrapper",analyzers, classRefObs)
    }
}