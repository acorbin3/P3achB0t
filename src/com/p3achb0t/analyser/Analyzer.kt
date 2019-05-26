package com.p3achb0t.analyser

import com.p3achb0t.Main.Data.dream
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

    fun parseJar(jar: JarFile){
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

        injectJARWithInterfaces(classes)


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

    private fun injectJARWithInterfaces(classes: MutableMap<String, ClassNode>) {
        //TODO add interface to client
        classes["client"]?.interfaces?.add("com/p3achb0t/hook_interfaces/Client")
//        classes["client"]?.methods?.listIterator()?.forEach { method ->
//            println(method.name + " " + method.desc)
        //            for(inst in method.instructions)
        //                println("\t" + inst.opcode + " " + inst.type)
//        }

        val methodsToImplement = listOf(
            "accountStatus", "baseX", "baseY", "cameraPitch", "cameraX",
            "cameraY", "cameraYaw", "cameraZ", "clickModifier", "crosshairColor", "currentWorld", "destinationX",
            "destinationY", "gameCycle", "gameState", "idleTime", "lastAction", "lastActionDifference",
            "lastActionDifferenceMod", "lastActionTime", "lastActionTimeMod", "lastButtonClick", "lastButtonClickModA",
            "lastButtonClickModM", "lastClickModifier", "lastClickModifierModA", "lastClickModifierModM", "lastClickX",
            "lastClickY", "loginState", "lowestAvailableCameraPitch", "mapAngle", "menuCount", "menuHeight",
            "menuWidth", "menuX", "menuY", "plane", "playerIndex", "selectedItemID", "selectedItemIndex",
            "selectionState", "zoomExact"
        )

        for (method in methodsToImplement) {
            injectMethod(method, classes)
        }


        //        getGameStateMethod.
        val out = JarOutputStream(FileOutputStream(File("./injected_jar.jar")))
        for (classNode in classes.values) {
            val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
//            println(classNode.name)
            classNode.accept(cw)
            out.putNextEntry(JarEntry(classNode.name + ".class"))
            out.write(cw.toByteArray())
        }
        out.flush()
        out.close()
    }

    private fun injectMethod(
        normalizedFieldName: String,
        classes: MutableMap<String, ClassNode>
    ) {
        val fieldName = dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.get(normalizedFieldName)?.obsName
        val signature = classes["client"]?.fields?.find { it.name == fieldName }?.signature
        val methodNode = MethodNode(ACC_PUBLIC, "get_$normalizedFieldName", "()I", signature, null)


        val classNodeName =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.get(normalizedFieldName)?.fieldTypeObsName

        val fieldDescriptor = "I"
        val isStatic = classes["client"]?.fields?.find { it.name == fieldName }?.access?.and(ACC_STATIC) != 0
        val fieldType = if (isStatic) GETSTATIC else GETFIELD
        if (!isStatic) {
            methodNode.visitVarInsn(ALOAD, 0)
        }
        methodNode.visitFieldInsn(fieldType, classNodeName, fieldName, fieldDescriptor)

        val multiplier =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.get(normalizedFieldName)?.modifier
        if (multiplier != null && multiplier != 0L) {
            println("Multiplier $multiplier")
            methodNode.visitLdcInsn(multiplier.toInt())
            methodNode.visitInsn(IMUL)
        }

        println("$classNodeName $fieldName $fieldDescriptor $fieldType $signature")
//        getGameStateMethod.instructions.insert(InsnNode(IRETURN))
        methodNode.visitInsn(IRETURN)


        methodNode.visitMaxs(0, 0)
        methodNode.visitEnd()
        methodNode.accept(classes["client"])


//        classes["client"]?.methods?.add(getGameStateMethod)
    }
}