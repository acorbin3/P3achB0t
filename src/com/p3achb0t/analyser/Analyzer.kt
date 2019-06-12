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
        //TODO add interface to client
        val classPath = "com/p3achb0t/hook_interfaces"
        dream?.classRefObs?.forEach { obsClass, clazzData ->
            if (obsClass in classes) {
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
                        getterList.add(getter)
                        println("\t\t$getter")
                    }
                }
            }
        }
//        classes["client"]?.interfaces?.add("com/p3achb0t/hook_interfaces/Client")
//        classes["client"]?.methods?.listIterator()?.forEach { method ->
//            println(method.name + " " + method.desc)
        //            for(inst in method.instructions)
        //                println("\t" + inst.opcode + " " + inst.type)
//        }
        val playerClazz = dream?.analyzers?.get(Player::class.java.simpleName)?.name

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
        getterList.add(GetterData("[L$playerClazz;", "players", returnFieldDescription = "[L$classPath/Player;"))
//        getterList.add(
//            GetterData(
//                "[[[L$playerClazz;",
//                "groundItemList",
//                returnFieldDescription = "[L$classPath/Player;"
//            )
//        )

//        for (method in getterList) {
//            injectMethod(method, classes, Client::class.java.simpleName)
//        }


//        val nameCompositeClazz = dream?.analyzers?.get(NameComposite::class.java.simpleName)?.name
//        classes[playerClazz]?.interfaces?.add("$classPath/Player")
//        val playerFieldList = ArrayList<GetterData>()
//        playerFieldList.add(GetterData("Z", "hidden"))
//        playerFieldList.add(GetterData("Z", "standingStill"))
//        playerFieldList.add(GetterData("I", "level"))
//        playerFieldList.add(GetterData("I", "overheadIcon"))
//        playerFieldList.add(GetterData("I", "skullIcon"))
//        playerFieldList.add(GetterData("I", "team"))
//        playerFieldList.add(
//            GetterData(
//                "L$nameCompositeClazz;",
//                "name",
//                returnFieldDescription = "L$classPath/NameComposite;"
//            )
//        )
//
//        for (method in playerFieldList) {
//            injectMethod(method, classes, Player::class.java.simpleName)
//        }
//
//        classes[nameCompositeClazz]?.interfaces?.add("$classPath/NameComposite")
//        val nameFieldList = ArrayList<GetterData>()
//        nameFieldList.add(GetterData("Ljava/lang/String;", "formatted"))
//        nameFieldList.add(GetterData("Ljava/lang/String;", "name"))
//
//        for (method in nameFieldList) {
//            injectMethod(method, classes, NameComposite::class.java.simpleName)
//        }

//        val actorClazz = dream?.analyzers?.get(com.p3achb0t.hook_interfaces.Actor::class.java.simpleName)?.name
//        classes[actorClazz]?.interfaces?.add("$classPath/Actor")
//        val fieldList = ArrayList<GetterData>()
//        fieldList.add(GetterData("I", "animation"))
//        fieldList.add(GetterData("I", "animationDelay"))
//        fieldList.add(GetterData("I", "combatTime"))
//        fieldList.add(GetterData("I", "frameOne"))
//        fieldList.add(GetterData("I", "frameTwo"))
//        fieldList.add(GetterData("I", "interacting"))
//        fieldList.add(GetterData("I", "localX"))
//        fieldList.add(GetterData("I", "localY"))
//        fieldList.add(GetterData("I", "orientation"))
//        fieldList.add(GetterData("I", "queueSize"))
//        fieldList.add(GetterData("I", "runtimeAnimation"))
//        fieldList.add(GetterData("I", "standAnimation"))
//
//        fieldList.add(GetterData("[I", "hitCycles"))
//        fieldList.add(GetterData("[I", "hitDamages"))
//        fieldList.add(GetterData("[I", "hitTypes"))
//        fieldList.add(GetterData("[I", "message"))
//        fieldList.add(GetterData("[I", "queueX"))
//        fieldList.add(GetterData("[I", "queueY"))
//
//        for (method in fieldList) {
//            injectMethod(method, classes, com.p3achb0t.hook_interfaces.Actor::class.java.simpleName)
//        }

//        val renderableClazz =
//            dream?.analyzers?.get(com.p3achb0t.hook_interfaces.Renderable::class.java.simpleName)?.name
//        classes[renderableClazz]?.interfaces?.add("$classPath/Renderable")
//        fieldList.clear()
//        fieldList.add(GetterData("I", "modelHeight"))
//        for (method in fieldList) {
//            injectMethod(method, classes, com.p3achb0t.hook_interfaces.Renderable::class.java.simpleName)
//        }




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

    private fun injectMethod(field: RuneLiteJSONClasses.FieldDefinition) {

    }

    private fun injectMethod(
        getterData: GetterData,
        classes: MutableMap<String, ClassNode>,
        analyserClass: String
    ) {
        val normalizedFieldName = getterData.methodName
        val fieldDescriptor = getterData.fieldDescription
        val returnFieldDescription =
            if (getterData.returnFieldDescription == "") getterData.fieldDescription else getterData.returnFieldDescription
        println("yyyy::class.java.simpleName: $analyserClass")
        val fieldName = dream?.analyzers?.get(analyserClass)?.fields?.find { it.field == normalizedFieldName }?.name
        val clazz = dream?.analyzers?.get(analyserClass)?.name
        println("CLass $clazz")
        val signature = classes[clazz]?.fields?.find { it.name == fieldName }?.signature
        val methodNode =
            MethodNode(ACC_PUBLIC, "$normalizedFieldName", "()$returnFieldDescription", signature, null)


        //TODO - This might not be correct
        val classNodeName =
            dream?.analyzers?.get(analyserClass)?.fields?.find { it.field == normalizedFieldName }?.descriptor

        val isStatic = classes[clazz]?.fields?.find { it.name == fieldName }?.access?.and(ACC_STATIC) != 0
        val fieldType = if (isStatic) GETSTATIC else GETFIELD
        if (!isStatic) {
            methodNode.visitVarInsn(ALOAD, 0)
        }
        methodNode.visitFieldInsn(fieldType, classNodeName, fieldName, fieldDescriptor)

        val multiplier =
            dream?.analyzers?.get(analyserClass)?.fields?.find { it.field == normalizedFieldName }?.decoder
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