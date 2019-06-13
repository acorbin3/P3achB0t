package com.p3achb0t.analyser

import com.p3achb0t.Main.Data.dream
import com.p3achb0t.class_generation.cleanType
import com.p3achb0t.class_generation.isBaseType
import com.p3achb0t.rsclasses.*
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes.*
import jdk.internal.org.objectweb.asm.tree.ClassNode
import jdk.internal.org.objectweb.asm.tree.MethodNode
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream


class Analyser{

    val classes: MutableMap<String, ClassNode> = mutableMapOf()

    fun parseJar(jar: JarFile, dream: DreamBotAnalyzer?) {
        val enumeration = jar.entries()
        val analyzers = mutableMapOf<String,RSClasses>()
        while(enumeration.hasMoreElements()){
            val entry = enumeration.nextElement()
            if(entry.name.endsWith(".class")){
                val classReader = ClassReader(jar.getInputStream(entry))
                val classNode = ClassNode()
                classReader.accept(classNode, 0)
                if (classNode.interfaces.size > 0) {

//                    println("${classNode.name} Interfaces")
                }
                for (_interface in classNode.interfaces) {
//                    println("\t $_interface")
                }
                classes[classNode.name] = classNode


                //Checking for SceneRespondRequest
            }
        }

        //        analyzeClasses(analyzers)

        injectJARWithInterfaces(classes, dream)


    }

    private fun analyzeClasses(analyzers: MutableMap<String, RSClasses>) {
        // Add analyzers
        analyzers[Node.deobName] = Node()
        analyzers[Client.deobName] = Client()
        analyzers[Canvas.deobName] = Canvas()
        analyzers[CacheNode.deobName] = CacheNode()
        analyzers[Renderable.deobName] = Renderable()
        analyzers[Actor.deobName] = Actor()
        analyzers[Player.deobName] = Player()
        analyzers[Npc.deobName] = Npc()
        analyzers[AnimatedObject.deobName] = AnimatedObject()

        for (analyzerObj in analyzers) {
            for (obClass in classes) {
                val classNode = obClass.value
                val analyzer = analyzerObj.value
                if (!analyzer.found) {
                    analyzer.analyze(classNode, analyzers)
                }
            }
        }
    }

    data class GetterData(
        val fieldDescription: String,
        val methodName: String,
        val clazz: String = "",
        val returnFieldDescription: String = ""
    )

    private fun injectJARWithInterfaces(classes: MutableMap<String, ClassNode>, dream: DreamBotAnalyzer?) {
        val classPath = "com/p3achb0t/hook_interfaces"
        dream?.classRefObs?.forEach { obsClass, clazzData ->
            if (obsClass in classes && obsClass.contains("client")) {
                val classInterface = "$classPath/${clazzData._class}"
                println("Adding class iterface to $obsClass $classInterface")
                classes[obsClass]?.interfaces?.add(classInterface)
                val getterList = ArrayList<GetterData>()
                clazzData.fields.forEach {
                    if (it.owner != "broken") {
                        println("\t Adding method ${it.field} descriptor ${it.descriptor}")
                        val getter: GetterData
                        if (isBaseType(it.descriptor)) {
                            getter = GetterData(it.descriptor, it.field)

                        } else {
                            val clazzName = dream.classRefObs[cleanType(it.descriptor)]?._class
                            var returnType = "L$classPath/$clazzName;"
                            val arrayCount = it.descriptor.count { char -> char == '[' }
                            returnType = "[".repeat(arrayCount) + returnType
                            //If the descriptor is a base java type, just use that
                            if (it.descriptor.contains("java")) {
                                returnType = it.descriptor
                            }
                            getter = GetterData(it.descriptor, it.field, returnFieldDescription = returnType)
                        }
                        if(!it.descriptor.contains("java")) {
                            getterList.add(getter)
                            println("\t\t$getter")
                        }
                    }
                }
                for (method in getterList) {
                    injectMethod(method, classes, clazzData._class)
                }
            }
        }
        val out = JarOutputStream(FileOutputStream(File("./injected_jar.jar")))
        for (classNode in classes.values) {
            val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
            classNode.accept(cw)
            out.putNextEntry(JarEntry(classNode.name + ".class"))
            out.write(cw.toByteArray())
        }
        out.flush()
        out.close()
    }

    enum class OpcodeType { LOAD, RETURN }

    private fun getReturnOpcode(fieldDescription: String): Int {

        return getOpcode(fieldDescription, OpcodeType.RETURN)
    }

    private fun getOpcode(fieldDescription: String, opcodeType: OpcodeType): Int {
        return when (fieldDescription[0]) {
            'F' -> if (opcodeType == OpcodeType.LOAD) FLOAT else FRETURN
            'D' -> if (opcodeType == OpcodeType.LOAD) DLOAD else DRETURN
            'J' -> if (opcodeType == OpcodeType.LOAD) LLOAD else LRETURN
            'I', 'B', 'Z', 'S' -> if (opcodeType == OpcodeType.LOAD) ILOAD else IRETURN
            else -> if (opcodeType == OpcodeType.LOAD) ALOAD else ARETURN
        }
    }

    private fun injectMethod(
        getterData: GetterData,
        classes: MutableMap<String, ClassNode>,
        analyserClass: String
    ) {
        val normalizedFieldName = getterData.methodName
        val field = dream?.analyzers?.get(analyserClass)?.fields?.find { it.field == normalizedFieldName }
        val classNodeName = field?.owner
        val fieldName = field?.name

        val fieldDescriptor = getterData.fieldDescription
        val returnFieldDescription =
            if (getterData.returnFieldDescription == "") getterData.fieldDescription else getterData.returnFieldDescription
        println("yyyy::class.java.simpleName: $analyserClass")


        val clazz = dream?.analyzers?.get(analyserClass)?.name
        println("CLass $clazz")
        val signature = classes[clazz]?.fields?.find { it.name == fieldName }?.signature
        val methodNode =
            MethodNode(ACC_PUBLIC, normalizedFieldName, "()$returnFieldDescription", signature, null)



        val isStatic = classes[clazz]?.fields?.find { it.name == fieldName }?.access?.and(ACC_STATIC) != 0
        val fieldType = if (isStatic) GETSTATIC else GETFIELD
        if (!isStatic) {
            methodNode.visitVarInsn(ALOAD, 0)
        }
        methodNode.visitFieldInsn(fieldType, classNodeName, fieldName, fieldDescriptor)

        val multiplier = field?.decoder
        if (multiplier != null && multiplier != 0L) {
            println("Multiplier $multiplier ${field.decoderType} ")
            if (field.decoderType == RuneLiteJSONClasses.DecoderType.LONG) {
                methodNode.visitLdcInsn(multiplier)
                methodNode.visitInsn(LMUL)
            } else {
                methodNode.visitLdcInsn(multiplier.toInt())
                methodNode.visitInsn(IMUL)
            }
        }

        println(
            "$classNodeName $normalizedFieldName $fieldName $fieldDescriptor $returnFieldDescription $fieldType $signature return: ${getReturnOpcode(
                fieldDescriptor
            )}"
        )
        methodNode.visitInsn(getReturnOpcode(fieldDescriptor))


        methodNode.visitMaxs(0, 0)
        methodNode.visitEnd()
        methodNode.accept(classes[clazz])

    }
}