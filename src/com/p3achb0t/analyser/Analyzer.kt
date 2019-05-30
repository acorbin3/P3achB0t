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

    data class GetterData(val fieldDescription: String, val methodName: String, val clazz: String = "")

    private fun injectJARWithInterfaces(classes: MutableMap<String, ClassNode>) {
        //TODO add interface to client
        classes["client"]?.interfaces?.add("com/p3achb0t/hook_interfaces/Client")
//        classes["client"]?.methods?.listIterator()?.forEach { method ->
//            println(method.name + " " + method.desc)
        //            for(inst in method.instructions)
        //                println("\t" + inst.opcode + " " + inst.type)
//        }
        val classPath = "com/p3achb0t/hook_interfaces"
        val getterList = ArrayList<GetterData>()
        getterList.add(GetterData("I", "accountStatus"))
        getterList.add(GetterData("I", "baseX"))
        getterList.add(GetterData("I", "baseY"))
        getterList.add(GetterData("I", "cameraPitch"))
        getterList.add(GetterData("I", "cameraX"))
        getterList.add(GetterData("I", "cameraY"))
        getterList.add(GetterData("I", "cameraYaw"))
        getterList.add(GetterData("I", "cameraZ"))
        getterList.add(GetterData("I", "clickModifier"))
        getterList.add(GetterData("I", "crosshairColor"))
        getterList.add(GetterData("I", "currentWorld"))
        getterList.add(GetterData("I", "destinationX"))
        getterList.add(GetterData("I", "destinationY"))
        getterList.add(GetterData("I", "gameCycle"))
        getterList.add(GetterData("I", "gameState"))
        getterList.add(GetterData("I", "idleTime"))
        getterList.add(GetterData("I", "lastAction"))
        getterList.add(GetterData("I", "lastActionDifference"))
        getterList.add(GetterData("I", "lastActionDifferenceMod"))
        getterList.add(GetterData("I", "lastActionTime"))
        getterList.add(GetterData("I", "lastActionTimeMod"))
        getterList.add(GetterData("I", "lastButtonClick"))
        getterList.add(GetterData("I", "lastButtonClickModA"))
        getterList.add(GetterData("I", "lastButtonClickModM"))
        getterList.add(GetterData("I", "lastClickModifier"))
        getterList.add(GetterData("I", "lastClickModifierModA"))
        getterList.add(GetterData("I", "lastClickModifierModM"))
        getterList.add(GetterData("I", "lastClickX"))
        getterList.add(GetterData("I", "lastClickY"))
        getterList.add(GetterData("I", "loginState"))
        getterList.add(GetterData("I", "lowestAvailableCameraPitch"))
        getterList.add(GetterData("I", "mapAngle"))
        getterList.add(GetterData("I", "menuCount"))
        getterList.add(GetterData("I", "menuHeight"))
        getterList.add(GetterData("I", "menuWidth"))
        getterList.add(GetterData("I", "menuX"))
        getterList.add(GetterData("I", "menuY"))
        getterList.add(GetterData("I", "plane"))
        getterList.add(GetterData("I", "playerIndex"))
        getterList.add(GetterData("I", "selectedItemID"))
        getterList.add(GetterData("I", "selectedItemIndex"))
        getterList.add(GetterData("I", "selectionState"))
        getterList.add(GetterData("I", "zoomExact"))
        getterList.add(GetterData("[I", "widgetBoundsX"))
        getterList.add(GetterData("[I", "widgetBoundsY"))
        getterList.add(GetterData("[I", "widgetHeights"))
        getterList.add(GetterData("Ljava/lang/String;", "username"))
        getterList.add(GetterData("Z", "isSpellSelected"))
        getterList.add(GetterData("Z", "isWorldSelectorOpen"))
//        getterList.add(GetterData("L$classPath/Player;", "players"))

        for (method in getterList) {
            injectMethod(method.fieldDescription, method.methodName, classes, Client::class.java.simpleName)
        }
        val playerClazz = dream?.analyzers?.get(Player::class.java.simpleName)?.obsName
        classes[playerClazz]?.interfaces?.add("$classPath/Player")
        val playerFieldList = ArrayList<GetterData>()
        playerFieldList.add(GetterData("Z", "hidden"))
        playerFieldList.add(GetterData("I", "level"))
//
//
        for (method in playerFieldList) {
            injectMethod(method.fieldDescription, method.methodName, classes, Player::class.java.simpleName)
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

    enum class OpcodeType { LOAD, RETURN }

    private fun getReturnOpcode(fieldDescription: String): Int {

        return getOpcode(fieldDescription, OpcodeType.RETURN)
    }

    private fun getLoadOpcode(fieldDescription: String): Int {

        return getOpcode(fieldDescription, OpcodeType.LOAD)
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
        fieldDescriptor: String,
        normalizedFieldName: String,
        classes: MutableMap<String, ClassNode>,
        analyserClass: String
    ) {
        println("yyyy::class.java.simpleName: $analyserClass")
        val fieldName = dream?.analyzers?.get(analyserClass)?.fields?.get(normalizedFieldName)?.obsName
        val clazz = dream?.analyzers?.get(analyserClass)?.obsName
        println("CLass $clazz")
        val signature = classes[clazz]?.fields?.find { it.name == fieldName }?.signature
        val methodNode = MethodNode(ACC_PUBLIC, "get_$normalizedFieldName", "()$fieldDescriptor", signature, null)


        val classNodeName =
            dream?.analyzers?.get(analyserClass)?.fields?.get(normalizedFieldName)?.fieldTypeObsName

        val isStatic = classes[clazz]?.fields?.find { it.name == fieldName }?.access?.and(ACC_STATIC) != 0
        val fieldType = if (isStatic) GETSTATIC else GETFIELD
        if (!isStatic) {
            methodNode.visitVarInsn(getLoadOpcode(fieldDescriptor), 0)
        }
        methodNode.visitFieldInsn(fieldType, classNodeName, fieldName, fieldDescriptor)

        val multiplier =
            dream?.analyzers?.get(analyserClass)?.fields?.get(normalizedFieldName)?.modifier
        if (multiplier != null && multiplier != 0L) {
            println("Multiplier $multiplier")
            methodNode.visitLdcInsn(multiplier.toInt())
            methodNode.visitInsn(IMUL)
        }

        println("$classNodeName $fieldName $fieldDescriptor $fieldType $signature")
        methodNode.visitInsn(getReturnOpcode(fieldDescriptor))


        methodNode.visitMaxs(0, 0)
        methodNode.visitEnd()
//        classes[clazz]?.methods?.add(methodNode)
        methodNode.accept(classes[clazz])


    }
}