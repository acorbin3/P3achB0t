package com.p3achb0t.analyser

import com.p3achb0t.Main
import com.p3achb0t.analyser.class_generation.cleanType
import com.p3achb0t.analyser.class_generation.isBaseType
import com.p3achb0t.analyser.runestar.ClassHook
import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.client.configs.Constants
import org.objectweb.asm.*
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*
import org.objectweb.asm.util.Printer
import org.objectweb.asm.util.Textifier
import org.objectweb.asm.util.TraceMethodVisitor
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.reflect.Modifier
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream


class Analyser{

    val logger = LoggerFactory.getLogger(Analyser::class.java)
    val classes: MutableMap<String, ClassNode> = mutableMapOf()

    private val printer: Printer = Textifier()
    private val mp: TraceMethodVisitor = TraceMethodVisitor(printer)

    fun createInjectedJar(jar: JarFile, runeStar: RuneStarAnalyzer?) {
        val enumeration = jar.entries()
        while(enumeration.hasMoreElements()){
            val entry = enumeration.nextElement()
            if(entry.name.endsWith(".class")){
                val classReader = ClassReader(jar.getInputStream(entry))
                val classNode = ClassNode()
                classReader.accept(classNode, 0)
                classes[classNode.name] = classNode
            }
        }

        injectJARWithInterfaces(classes, runeStar)


    }


    data class GetterData(
            val fieldDescription: String,
            val methodName: String,
            val clazz: String = "",
            val returnFieldDescription: String = ""
    )

    data class InvokerData(
            val into: String,
            val owner: String,
            val invokeMethodName: String,
            val argumentDescription: String,
            val returnDescription: String = "",
            val normalizedMethodName: String,
            val isInterface: Boolean = false,
            val instanceCast: String? = null,
            val argsCheckCastDesc: String? = null
    )

//    public class  Invoker(String into, String methodLoc, String invMethName,
//                          String argsDesc, String returnDesc, String methodName, boolean isInterface, String instanceCast, String argsCheckCastDesc)

    private fun injectJARWithInterfaces(classes: MutableMap<String, ClassNode>, runeStar: RuneStarAnalyzer?) {
        val classPath = "com/p3achb0t/api/interfaces"

        //delete opaquePredicates
        println("Starting - Deleting opaque Predicates")
        classes.forEach { clazz ->
            clazz.value.methods.iterator().forEach { methodNode ->
//                println("clazz: ${clazz.key}.${methodNode.name}")
                val lastParamIndex = methodNode?.lastParamIndex!!
                val instructions = methodNode.instructions?.iterator()!!
                var returns = 0
                var exceptions = 0
                while (instructions.hasNext()) {
                    val insn = instructions.next()
//                    insn.accept(mp)
//                    val sw = StringWriter()
//                    printer.print(PrintWriter(sw))
//                    printer.getText().clear()
//                    println("\t${sw.toString().replace("\n", "")}")

                    val toDelete = if (insn.matchesReturn(lastParamIndex)) {
                        returns++
                        4
                    } else if (insn.matchesException(lastParamIndex)) {
                        exceptions++
                        7
                    } else {
                        continue
                    }
//                    println("\tdeleting: $toDelete")
                    val label = (insn.next.next as JumpInsnNode).label.label
                    instructions.remove()
                    repeat(toDelete - 1) {
                        instructions.next()
                        instructions.remove()
                    }
                    instructions.add(JumpInsnNode(GOTO, LabelNode(label)))
                }
            }
        }

        println("Finished - Deleting opaque Predicates")


        runeStar?.classRefObs?.forEach { obsClass, clazzData ->

            if(clazzData.`class` == "Client") {
                injectScriptManagerField(classes[clazzData.name]!!)
                injectFieldProxy(classes[clazzData.name]!!)
                AsmUtil.addInterface(classes[clazzData.name]!!,"com/p3achb0t/api/interfaces/IOHandler")
                AsmUtil.addInterface(classes[clazzData.name]!!,"com/p3achb0t/client/injection/InstanceManagerInterface")
                injectCustomClient(classes[clazzData.name]!!)
                clazzData.fields.forEach{
                    if(it.field .contains("MouseHandler_instance")){
                        println("Found field MouseHandler_instance ${it.toString}")
                        AsmUtil.addStaticMethod(
                                classes[clazzData.name]!!,
                                "getMouse",
                                "()Lcom/p3achb0t/api/interfaces/Mouse;",//()Lcom/p3achb0t/client/interfaces/io/Mouse;
                                it.owner,
                                it.name,
                                "L${it.owner};"
                        ) // MouseHandler_instance

                    }
                    else if(it.field.contains("KeyHandler_instance")){
                        println("Found field KeyHandler_instance ${it.toString}")
                        AsmUtil.addStaticMethod(
                                classes[clazzData.name]!!,
                                "getKeyboard",
                                "()Lcom/p3achb0t/api/interfaces/Keyboard;",
                                it.owner,
                                it.name,
                                "L${it.owner};"
                        ) // KeyHandler_instance
                    }
                }


            }

//            if (clazzData.`class` == "GameShell") {
//                injectGameLoop(classes[clazzData.name]!!)
//            }

            if(clazzData.`class` == "TaskHandler") {
                injectSocket(classes[clazzData.name]!!)
            }

            if (clazzData.`class` == "Canvas") {
                injectCanvas(classes[clazzData.name]!!)
                //println("${clazzData.name} : ${clazzData.`super`} ")
            }

            val classInterface = "$classPath/${clazzData.`class`}"
            if (!classInterface.contains("Usernamed")) {
                //println("Adding class iterface to $obsClass $classInterface")
                classes[obsClass]?.interfaces?.add(classInterface)
            }

            //Injecting all the fields
            val getterList = ArrayList<GetterData>()
            clazzData.fields.forEach {
                if (it.owner != "broken" && !it.field.contains("getLocalUser")) {
                    //println("\t Adding method ${it.field} descriptor ${it.descriptor}")
                    val getter: GetterData
                    if (isBaseType(it.descriptor)) {
                        getter = GetterData(it.descriptor, it.field)

                    } else {
                        val clazzName = runeStar.classRefObs[cleanType(it.descriptor)]?.`class`
                        var returnType = "L$classPath/$clazzName;"
                        val arrayCount = it.descriptor.count { char -> char == '[' }
                        returnType = "[".repeat(arrayCount) + returnType
                        //If the descriptor is a base java type, just use that
                        if (it.descriptor.contains("java")) {
                            returnType = it.descriptor
                        }
                        getter = GetterData(it.descriptor, it.field, returnFieldDescription = returnType)
                    }
                    if (!it.descriptor.contains("java")  ) {
                        getterList.add(getter)
                        //println("\t\t$getter")
                    }
                    else{
                        if(it.descriptor.contains("String")){
                            getterList.add(getter)
                            //println("\t\t$getter")
                        }else {
                            //println("\t\t!@#$# ${it.descriptor}")
                        }
                    }
                }
            }
            for (method in getterList) {
                if (method.fieldDescription != "")
                    injectFieldGetter(method, classes, clazzData.`class`, runeStar)
            }



            //Inject doAction
            if(clazzData.`class` == "Client") {
                injectDoActionHook(runeStar, clazzData, classes)
            }

            //Inject doAction callback
            //TODO - add doActionCallback
//            if (clazzData.`class` == "Client") {
//                val methodHook = runeStar.analyzers[clazzData.`class`]?.methods?.find { it.method == "doAction" }
////                println("MethodHook: $methodHook")
//                //Find the addMessage method, then inject the call back at the front
////                println("looking at class ${methodHook?.owner}")
//                classes[methodHook?.owner]?.methods?.forEach { methodNode ->
////                    println("Looking at method: ${methodNode.name}")
//                    if (methodNode.name == methodHook?.name ) {
////                        println("methodNode.desc: ${methodNode.desc}")
////                        println("Time to insert instructions")
//                        val il = InsnList()
//                        il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))
//                        il.add(VarInsnNode(ILOAD, 0))
//                        il.add(VarInsnNode(ILOAD, 1))
//                        il.add(VarInsnNode(ILOAD, 2))
//                        il.add(VarInsnNode(ILOAD, 3))
//                        il.add(VarInsnNode(ALOAD, 4))
//                        il.add(VarInsnNode(ALOAD, 5))
//                        il.add(VarInsnNode(ILOAD, 6))
//                        il.add(VarInsnNode(ILOAD, 7))
//                        il.add(VarInsnNode(ILOAD, 8))
//                        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "doActionCallback", methodHook?.descriptor))
//                        methodNode.instructions.insert(il)
//                    }
//                }
//            }

            //Inject NPc Model callback
            if (clazzData.`class` == "Npc") {
                println("Injecting getModel callback for NPC")
                val methodHook = runeStar.analyzers[clazzData.`class`]?.methods?.find { it.method == "getModel" }
                classes[methodHook?.owner]?.methods?.forEach { methodNode ->
                    if (methodNode.name == methodHook?.name) {
//                        println("found getModel at method: ${methodNode.name}")
                        val il = InsnList()
                        il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))
                        il.add(VarInsnNode(ILOAD, 1))
                        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "getModelCallback", "(I)V"))
                        methodNode.instructions.insert(il)
                    }
                }
            }

            //Inject varBit
            if(clazzData.`class` == "Client") {
                val methodHook = runeStar.analyzers[clazzData.`class`]?.methods?.find { it.method == "getVarbit" }
//                println("MethodHook: $methodHook")
                val varBitMethodNode = MethodNode(ACC_PUBLIC, "getVarbit", "(I)I", null, null)
                varBitMethodNode.visitVarInsn(ILOAD, 1)
                varBitMethodNode.visitInsn(ICONST_0)
                varBitMethodNode.visitMethodInsn(INVOKESTATIC, methodHook?.owner, methodHook?.name, methodHook?.descriptor)

                varBitMethodNode.visitInsn(Opcodes.IRETURN)
                varBitMethodNode.visitEnd()

                classes[runeStar.analyzers[clazzData.`class`]?.name]?.methods?.add(varBitMethodNode)
            }



            // Inject getModel for the Entity
            val listOfModelInjections = listOf<String>("Entity")
            if(clazzData.`class` in listOfModelInjections){
                val methodHook = runeStar.analyzers[clazzData.`class`]?.methods?.find { it.method == "getModel" }
                println("${clazzData.`class`} MethodHook: $methodHook")
                //The method descriptor from the hooks looks like this: "descriptor": "(IZI)[B"
                //the data inbetween the () is the argument descriptor, and the data after ) is the return descriptor
                val list = methodHook?.descriptor?.split(")")!!
                val argumentDescription = list[0] + ")" // Add back in the )
                val returnDescriptor = list[1]
                val clazzName = runeStar.classRefObs[cleanType(returnDescriptor)]?.`class`

                var returnType = "L$classPath/$clazzName;"
//                println("Returntype $returnType")

                //Find the class.method, look for the opaque predicate
//                println("methodHook.owner: ${methodHook.owner} methodHook.name ${methodHook.name}")
                val foundMethod = classes[methodHook.owner]?.methods?.first { it.name == methodHook.name }
                var loadFound = false


                val methodNode = MethodNode(ACC_PUBLIC, methodHook.method, "()"+returnType, null, null)

                methodNode.visitVarInsn(ALOAD, 0)
                methodNode.visitInsn(ICONST_0)
                methodNode.visitMethodInsn(INVOKEVIRTUAL, methodHook.owner, methodHook.name, methodHook.descriptor)

                val cast = "$classPath/$clazzName"
                methodNode.visitTypeInsn(CHECKCAST, cast)
                methodNode.visitInsn(Opcodes.ARETURN)
                methodNode.visitEnd()

                classes[runeStar.analyzers[clazzData.`class`]?.name]?.methods?.add(methodNode)
            }



            //Inject addMessage
            if (clazzData.`class` == "ChatChannel") {
                println("Adding chat listener callback")
                val methodHook = runeStar.analyzers[clazzData.`class`]?.methods?.find { it.method == "addMessage" }
//                println("MethodHook: $methodHook")
                val list = methodHook?.descriptor?.split(")")!!
                val argumentDescription = list[0] + ")" // Add back in the )
                val returnDescriptor = list[1]
                val clazzName = runeStar.classRefObs[cleanType(returnDescriptor)]?.`class`

                val returnType = "L$classPath/$clazzName;"
//                println("Return type $returnType")

                //Find the addMessage method, then inject the call back at the front
                classes[runeStar.analyzers[clazzData.`class`]?.name]?.methods?.forEach { methodNode ->
//                    println("Looking at method: ${methodNode.name}")
                    if (methodNode.name == methodHook.name && methodNode.desc.contains("ILjava/lang/String;Ljava/lang/String;Ljava/lang/String")) {
//                        println("methodNode.desc: ${methodNode.desc}")
//                        println("Time to insert instructions")
                        val il = InsnList()
                        il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))
                        il.add(VarInsnNode(ILOAD, 1))
                        il.add(VarInsnNode(ALOAD, 2))
                        il.add(VarInsnNode(ALOAD, 3))
                        il.add(VarInsnNode(ALOAD, 4))
                        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "notifyMessage", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"))
                        methodNode.instructions.insert(il)

                    }
                }
            }



            // Bypass focus events
            if(clazzData.`class` == "GameShell"){
                println("Adding focus blocker")
                injectFocusBlocker(classes[clazzData.name]!!)
            }


//            println("Methods:~~~~~~")
//            addInvokeMethods(clazzData, runeStar, classPath, classes)


            if (clazzData.`class` == "KeyHandler") {
                AsmUtil.setSuper(classes[clazzData.name]!!,"java/lang/Object","com/p3achb0t/client/injection/io/AbstractKeyboard")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "keyPressed", "_keyPressed")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "keyReleased", "_keyReleased")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "keyTyped", "_keyTyped")
            }

            if (clazzData.`class` == "MouseHandler") {
                AsmUtil.setSuper(classes[clazzData.name]!!, "java/lang/Object", "com/p3achb0t/client/injection/io/AbstractMouse") // "com/p3achb0t/client/interfaces/io/Mouse"
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseClicked", "_mouseClicked")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseDragged", "_mouseDragged")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseEntered", "_mouseEntered")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseExited", "_mouseExited")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseMoved", "_mouseMoved")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mousePressed", "_mousePressed")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseReleased", "_mouseReleased")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseWheelMoved", "_mouseWheelMoved")

                // Find the correct fields for getX and getY within the Client and inject them into our MouseHandler class
                runeStar.classRefObs.forEach { obsClass1, clazzData1 ->
                    if(clazzData1.`class` == "Client") {
                        clazzData1.fields.forEach {
                            if (it.field.contains("MouseHandler_x") && !it.field.contains("MouseHandler_x0")) {
                                println("Found field MouseHandler_x ${it.toString}")
                                AsmUtil.addMethod(
                                        classes[clazzData.name]!!,
                                        "getX",
                                        "()I",
                                        it.owner,
                                        it.name,
                                        "I",
                                        it.decoder!!.toInt()
                                ) // MouseHandler_x
                            } else if (it.field.contains("MouseHandler_y") && !it.field.contains("MouseHandler_y0")) {
                                println("Found field MouseHandler_y ${it.toString}")
                                AsmUtil.addMethod(
                                        classes[clazzData.name]!!,
                                        "getY",
                                        "()I",
                                        it.owner,
                                        it.name,
                                        "I",
                                        it.decoder!!.toInt()
                                ) // MouseHandler_y
                            }
                        }
                    }
                }


            }
        }

        // Find all doAction calls
        if(true) {
            val doActionMethodHook = runeStar?.analyzers!!.get("Client")?.methods?.find { it.method == "doAction" }
            classes.forEach { clazz ->
                clazz.value.methods.iterator().forEach { methodNode ->
                    //Skip our doAction call
                    if (methodNode.name != "doAction") {

                        methodNode.instructions.iterator().forEach { abstractNode ->
                            if (abstractNode.opcode == INVOKESTATIC) {
                                val mn = (abstractNode as MethodInsnNode)
                                if (mn.owner == doActionMethodHook?.owner && mn.name == doActionMethodHook?.name) {
//                                println("class: ${clazz.key}" )
//                                println("\tmethod: ${methodNode.name}")
//                                println("\t\tFound invokestatic call ${mn.owner}.${mn.name}")
                                    val il = InsnList()
                                    il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))
                                    il.add(MethodInsnNode(INVOKESTATIC, "com/p3achb0t/detours/Detours", "doAction", "(IIIILjava/lang/String;Ljava/lang/String;IIBLcom/p3achb0t/client/injection/InstanceManager;)V", false))
                                    //doAction
                                    methodNode.instructions.insertBefore(mn, il)
                                    methodNode.instructions.remove(mn)
                                }
                            }
                        }
                    }
                }
            }
        }

        // TODO Find a way to auto update - open os? https://raw.githubusercontent.com/open-osrs/runelite/master/runescape-client/src/main/java/RouteStrategy.java
        val garbageCollectClass = "aw"
        val garbageCollectMethod = "ai"
        //OpenRS key search: getGcDuration
        var gcInjected = false
        gcRoot@for (method in classes[garbageCollectClass]!!.methods) {
            if (method.name == garbageCollectMethod) {
                val instructions = method.instructions.iterator()
                instructions.forEach {
                    if(it.opcode == IRETURN){
                        println("Found IRETURN. Prev: ${it.previous.opcode}, prev.prev?: ${it.previous.previous.opcode}. prev prev prev: ${it.previous.previous.previous.opcode}")
                    }
                    if (!gcInjected
                            && it.opcode == IRETURN
                            && it.previous.opcode == ILOAD) {
                        println("Injecting GC check return.")
                        method.instructions.set(it.previous, InsnNode(ICONST_0))
                        gcInjected = true
                    }
                }
            }
        }
        if (!gcInjected) println("Failed to inject GC duration bypass.")

        val createRandDatClass = "cm"
        val createRandDatMethod = "s"
        var datInjected = 0
        randRoot@for (method in classes[createRandDatClass]!!.methods) {
            if (method.name == createRandDatMethod) {
                val instructions = method.instructions.iterator()
                while (instructions.hasNext()) {
                    val i = instructions.next()
                    if (i is LdcInsnNode && i.cst.toString() == "random.dat") {
                        val ni = FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;")
                        method.instructions.set(i, ni)
                        val il = InsnList()
                        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "getAccount", "()Lcom/p3achb0t/client/accounts/Account;"))
                        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/accounts/Account", "getRandomDat", "()Ljava/lang/String;"))
                        method.instructions.insert(ni, il)
                        datInjected += 1
                        if (datInjected == 2) {
                            println("Replaced random.dat references.")
                            break@randRoot
                        }
                    }
                }
            }
        }
        if (datInjected < 2) println("Failed to inject random.dat bypass.")

        val path = System.getProperty("user.dir")
        val out = JarOutputStream(FileOutputStream(File("$path/${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}")))
        for (classNode in classes.values) {
            val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
            classNode.accept(cw)
            out.putNextEntry(JarEntry(classNode.name + ".class"))
            out.write(cw.toByteArray())
            out.closeEntry()
        }

        out.putNextEntry(JarEntry("ProxySocket.class"))
        out.write(putClasses("/ProxySocket.class"))
        out.closeEntry()

        out.putNextEntry(JarEntry("ProxyConnection.class"))
        out.write(putClasses("/ProxyConnection.class"))

        out.closeEntry()

        out.flush()
        out.close()
    }


    private fun AbstractInsnNode.matchesReturn(lastParamIndex: Int): Boolean {
        val i0 = this
        if (i0.opcode != ILOAD) return false
        i0 as VarInsnNode
        if (i0.`var` != lastParamIndex) return false
        val i1 = i0.next
        if (!i1.isConstantIntProducer) return false
        val i2 = i1.next
        if (!i2.isIf) return false
        val i3 = i2.next
        if (!i3.isReturn) return false
        return true
    }

    private val ISE_INTERNAL_NAME = Type.getInternalName(IllegalStateException::class.java)

    private fun AbstractInsnNode.matchesException(lastParamIndex: Int): Boolean {
        val i0 = this
        if (i0.opcode != ILOAD) return false
        i0 as VarInsnNode
        if (i0.`var` != lastParamIndex) return false
        val i1 = i0.next
        if (!i1.isConstantIntProducer) return false
        val i2 = i1.next
        if (!i2.isIf) return false
        val i3 = i2.next
        if (i3.opcode != NEW) return false
        val i4 = i3.next
        if (i4.opcode != DUP) return false
        val i5 = i4.next
        if (i5.opcode != INVOKESPECIAL) return false
        i5 as MethodInsnNode
        if (i5.owner != ISE_INTERNAL_NAME) return false
        val i6 = i5.next
        if (i6.opcode != ATHROW) return false
        return true
    }

    val AbstractInsnNode.isConstantIntProducer: Boolean get() {
        return when (opcode) {
            Opcodes.LDC -> (this as LdcInsnNode).cst is Int
            Opcodes.SIPUSH, Opcodes.BIPUSH, Opcodes.ICONST_0, Opcodes.ICONST_1, Opcodes.ICONST_2, Opcodes.ICONST_3, Opcodes.ICONST_4, Opcodes.ICONST_5, Opcodes.ICONST_M1 -> true
            else -> false
        }
    }

    val AbstractInsnNode.constantIntProduced: Int get() {
        return when (opcode) {
            Opcodes.LDC -> (this as LdcInsnNode).cst as Int
            Opcodes.SIPUSH, Opcodes.BIPUSH -> (this as IntInsnNode).operand
            Opcodes.ICONST_0 -> 0
            Opcodes.ICONST_1 -> 1
            Opcodes.ICONST_2 -> 2
            Opcodes.ICONST_3 -> 3
            Opcodes.ICONST_4 -> 4
            Opcodes.ICONST_5 -> 5
            Opcodes.ICONST_M1 -> -1
            else -> error(this)
        }
    }

    private val MethodNode.lastParamIndex: Int get() {
        val offset = if (Modifier.isStatic(access)) 1 else 0
        return (Type.getArgumentsAndReturnSizes(desc) shr 2) - offset - 1
    }

    private fun passingVal(pushed: Int, ifOpcode: Int): Int {
        return when(ifOpcode) {
            IF_ICMPEQ -> pushed
            IF_ICMPGE,
            IF_ICMPGT -> pushed + 1
            IF_ICMPLE,
            IF_ICMPLT,
            IF_ICMPNE -> pushed - 1
            else -> error(ifOpcode)
        }
    }

    private val AbstractInsnNode.isIf: Boolean get() {
        return this is JumpInsnNode && opcode != Opcodes.GOTO
    }

    private val AbstractInsnNode.isReturn: Boolean get() {
        return when (opcode) {
            RETURN, ARETURN, DRETURN, FRETURN, IRETURN, LRETURN -> true
            else -> false
        }
    }

    private fun addInvokeMethods(clazzData: ClassHook, runeStar: RuneStarAnalyzer, classPath: String, classes: MutableMap<String, ClassNode>) {
        val invokeList = ArrayList<InvokerData>()
        clazzData.methods.forEach {
            if (it.method.contains("getVarbit")) { //Username has an error, skip
                println("method:${it.method} name:${it.name} owner:${it.owner} descriptor:${it.descriptor} arguments:${it.parameters.toString()}")


                //The method descriptor from the hooks looks like this: "descriptor": "(IZI)[B"
                //the data inbetween the () is the argument descriptor, and the data after ) is the return descriptor
                val list = it.descriptor.split(")")
                val argumentDescription = list[0] + ")" // Add back in the )
                val returnDescriptor = list[1]

                println("Return Descriptor: $returnDescriptor")

                val invokeData: InvokerData
                if (isBaseType(returnDescriptor) || returnDescriptor == "V") {
                    invokeData = InvokerData(
                            into = clazzData.`class`,
                            owner = it.owner,
                            invokeMethodName = it.name,
                            argumentDescription = argumentDescription,
                            returnDescription = returnDescriptor,
                            normalizedMethodName = it.method
                    )

                } else {
                    println("Clean return: ${cleanType(returnDescriptor)}")
                    val clazzName = runeStar.classRefObs[cleanType(returnDescriptor)]?.`class`

                    var returnType = "L$classPath/$clazzName;"
                    val arrayCount = returnDescriptor.count { char -> char == '[' }
                    returnType = "[".repeat(arrayCount) + returnType
                    println("class reference: $clazzName. ReturnType: $returnType")
                    //If the descriptor is a base java type, just use that
                    if (returnType.contains("java")) {
                        println("Descirptor contained java: ${it.descriptor}")
                        returnType = returnDescriptor
                    }
                    invokeData = InvokerData(
                            into = clazzData.`class`,
                            owner = it.owner,
                            invokeMethodName = it.name,
                            argumentDescription = argumentDescription,
                            returnDescription = returnType,
                            normalizedMethodName = it.method
                    )
                }
                if (!returnDescriptor.contains("java")) {
                    invokeList.add(invokeData)
                    println("\t\t$invokeData")
                } else {
                    if (returnDescriptor.contains("String")) {
                        invokeList.add(invokeData)
                        println("\t\t$invokeData")
                    } else {
                        //println("\t\t!@#$# ${it.descriptor}")
                    }
                }
            }
        }
        for (invokeMethod in invokeList) {
            injectInvoker(invokeMethod, classes, clazzData.`class`, runeStar)
        }
    }

    enum class OpcodeType { LOAD, RETURN }

    private fun getReturnOpcode(fieldDescription: String): Int {

        return getOpcode(fieldDescription, OpcodeType.RETURN)
    }

    private fun getLoadOpcode(description: String): Int {
        return getOpcode(description,OpcodeType.LOAD)
    }
    private fun getOpcode(fieldDescription: String, opcodeType: OpcodeType): Int {
        return when (fieldDescription[0]) {
            'F' -> if (opcodeType == OpcodeType.LOAD) FLOAT else FRETURN
            'D' -> if (opcodeType == OpcodeType.LOAD) DLOAD else DRETURN
            'J' -> if (opcodeType == OpcodeType.LOAD) LLOAD else LRETURN
            'I', 'B', 'Z', 'S', 'C' -> if (opcodeType == OpcodeType.LOAD) ILOAD else IRETURN
            'V'->  RETURN // void, method desc
            else -> if (opcodeType == OpcodeType.LOAD) ALOAD else ARETURN
        }
    }

    private fun injectCanvas(classNode: ClassNode) {
        classNode.superName = "com/p3achb0t/client/injection/RsCanvas"
        for (method in classNode.methods) {
            if (method.name == "<init>") {
                val i: InsnList = method.instructions
                for (insn in i) {
                    if (insn.opcode == Opcodes.INVOKESPECIAL) {
                        if (insn is MethodInsnNode) {
                            val mnode = insn
                            mnode.owner = "com/p3achb0t/client/injection/RsCanvas"
                            mnode.desc = "(Lcom/p3achb0t/client/injection/InstanceManager;)V"
                            val ins = InsnList()
                            ins.add(FieldInsnNode(GETSTATIC, "client", "script","Lcom/p3achb0t/client/injection/InstanceManager;"))
                            i.insert(insn.previous, ins)
                            method.maxStack += 3
                            return
                        }
                    }
                }
            }
        }
    }

    private fun injectGameLoop(classNode: ClassNode) {
        for (method in classNode.methods) {
            if (method.name != "run") {
                continue
            }

            val insn = method.instructions.iterator()
            while (insn.hasNext()) {
                val i = insn.next()
                if (i.opcode == GETFIELD) {
                    val field = i as FieldInsnNode
                    if (field.desc == "Ljava/awt/Canvas;") {
                        println("---> ${field.desc}")
                        while (insn.hasNext()) {
                            val j = insn.next()
                            if (j.opcode == GOTO) {
                                val prev = j.previous
                                val il = InsnList()
                                il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))
                                il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "gameLoop", "()V"))
                                //il.add(FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"))
                                //il.add(LdcInsnNode("."));
                                //il.add(MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V"))
                                method.instructions.insert(prev, il)
                                method.maxStack += 1
                                println("found")
                                break

                            }

                        }
                    }

                }
            }

        }
    }

    private fun putClasses(name: String): ByteArray {
        println("Putting class $name")

        val file1 = File("${Constants.USER_DIR}/src/"+name)
        if(!file1.exists()){
            File("${Constants.USER_DIR}/src/").mkdir()
            val input = URL("https://raw.githubusercontent.com/acorbin3/P3achB0t/master/src//$name").openStream()
            Files.copy(input, Paths.get("${Constants.USER_DIR}/src/"+name), StandardCopyOption.REPLACE_EXISTING)
        }
        val file: InputStream? = Main.javaClass.getResourceAsStream(name) ?: return File("${Constants.USER_DIR}/src/"+name).readBytes()
        return file?.readBytes()!!
    }

    private fun injectDoActionHook(runeStar: RuneStarAnalyzer, clazzData: ClassHook, classes: MutableMap<String, ClassNode>) {
        val methodHook = runeStar.analyzers[clazzData.`class`]?.methods?.find { it.method == "doAction" }
        println("MethodHook: $methodHook")

        val label = LabelNode(Label())

        val doActionMethodNode = MethodNode(ACC_PUBLIC, "doAction", "(IIIILjava/lang/String;Ljava/lang/String;IIB)V", null, null)
        val il = InsnList()
        
        il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "getActionScript", "()Lcom/p3achb0t/api/script/ActionScript;", false))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/api/script/ActionScript", "getCtx", "()Lcom/p3achb0t/api/Context;", false))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/api/Context", "getMouse","()Lcom/p3achb0t/api/userinputs/Mouse;",false))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/api/userinputs/Mouse", "getOverrideDoActionParams","()Z",false))
        il.add(JumpInsnNode(IFEQ,label)) // Jump to regular processing
        val label2 = LabelNode(Label())
        il.add(label2)
        il.add(FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"))
        il.add(LdcInsnNode("overriding DoAction params"))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",false))

        //reset override doAction back to
        val label3 = LabelNode(Label())
        il.add(label3)
        il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "getActionScript", "()Lcom/p3achb0t/api/script/ActionScript;", false))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/api/script/ActionScript", "getCtx", "()Lcom/p3achb0t/api/Context;", false))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/api/Context", "getMouse","()Lcom/p3achb0t/api/userinputs/Mouse;",false))
        il.add(InsnNode(ICONST_0))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/api/userinputs/Mouse", "setOverrideDoActionParams","(Z)V",false))
//
//        // load all the params
        il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))
        addDoActionParamGetter(il, "getActionParam", "()I")
        addDoActionParamGetter(il, "getWidgetID", "()I")
        addDoActionParamGetter(il, "getMenuAction", "()I")
        addDoActionParamGetter(il, "getId", "()I")
        addDoActionParamGetter(il, "getMenuOption", "()Ljava/lang/String;")
        addDoActionParamGetter(il, "getMenuTarget", "()Ljava/lang/String;")
        addDoActionParamGetter(il, "getMouseX", "()I")
        addDoActionParamGetter(il, "getMouseY", "()I")

//        il.add(InsnNode(ICONST_0))
        il.add(VarInsnNode(ILOAD, 9))
//        //Finally call the doAction internal method
        il.add(MethodInsnNode(INVOKESTATIC, methodHook?.owner, methodHook?.name, methodHook?.descriptor,false))
        il.add(InsnNode(POP))
        il.add(InsnNode(RETURN))


        //Normal processing
        il.add(label)
        il.add(FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"))
        il.add(LdcInsnNode("Normal DoAction params"))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",false))
        il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))
        il.add(VarInsnNode(ILOAD, 1))
        il.add(VarInsnNode(ILOAD, 2))
        il.add(VarInsnNode(ILOAD, 3))
        il.add(VarInsnNode(ILOAD, 4))
        il.add(VarInsnNode(ALOAD, 5))
        il.add(VarInsnNode(ALOAD, 6))
        il.add(VarInsnNode(ILOAD, 7))
        il.add(VarInsnNode(ILOAD, 8))
        il.add(VarInsnNode(ILOAD, 9))
//        il.add(InsnNode(ICONST_0))
        il.add(MethodInsnNode(INVOKESTATIC, methodHook?.owner, methodHook?.name, methodHook?.descriptor,false))
        il.add(InsnNode(POP))
        il.add(InsnNode(RETURN))

        doActionMethodNode.instructions.add(il)

        classes[runeStar.analyzers[clazzData.`class`]?.name]?.methods?.add(doActionMethodNode)
    }

    //Simple methon used to inject the field retrieval to set up the DoActioncall
    private fun addDoActionParamGetter(il: InsnList, paramName: String, paramDescription: String) {
        val label = LabelNode(Label())
        il.add(label)
        il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "getActionScript", "()Lcom/p3achb0t/api/script/ActionScript;", false))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/api/script/ActionScript", "getCtx", "()Lcom/p3achb0t/api/Context;", false))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/api/Context", "getMouse", "()Lcom/p3achb0t/api/userinputs/Mouse;", false))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/api/userinputs/Mouse", "getDoActionParams", "()Lcom/p3achb0t/api/userinputs/DoActionParams;", false))
        il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/api/userinputs/DoActionParams", paramName, paramDescription, false))
    }

    //This method will inject a boolean check in for the scriptManager object for field blockFocus
    // the gain focus only set 1 time, then block all get focus events
    // Always block loseFocus events when blockFocus is true
    private fun injectFocusBlocker(classNode: ClassNode){
        for (method in classNode.methods) {
            if (method.name.contains("focusLost")) {
                val il = InsnList()
                val labelNode = LabelNode(Label())


                il.add(FieldInsnNode(GETSTATIC, "client", "script","Lcom/p3achb0t/client/injection/InstanceManager;"))
                il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "getBlockFocus","()Z"))


                il.add(JumpInsnNode(IFEQ,labelNode))
                il.add(labelNode)
                //il.add(FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"))
                //il.add(LdcInsnNode("#####Blocked lost focus"))
                //il.add(MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V"))


                il.add(InsnNode(RETURN))
                method.instructions.insert(il)
            }
            else if (method.name.contains("focusGained")) {
                //create new label that just returns
                val il = InsnList()
                val labelNode = LabelNode(Label())

                il.add(FieldInsnNode(GETSTATIC, "client", "script","Lcom/p3achb0t/client/injection/InstanceManager;"))
                il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "getBlockFocus","()Z"))

                il.add(JumpInsnNode(IFNE,labelNode))
                //Jump to return
                il.add(FieldInsnNode(GETSTATIC, "client", "script","Lcom/p3achb0t/client/injection/InstanceManager;"))
                il.add(InsnNode(ICONST_1))
                il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/client/injection/InstanceManager", "setBlockFocus","(Z)V"))
                il.add(labelNode)
                //il.add(FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"))
                //il.add(LdcInsnNode("#####Blocked gained focus"))
                //il.add(MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V"))





                il.add(InsnNode(RETURN))
                method.instructions.insert(il)
            }

        }
    }

    private fun injectScriptManagerField(classNode: ClassNode) {
        val node = FieldNode(ACC_PUBLIC + ACC_STATIC, "script", "Lcom/p3achb0t/client/injection/InstanceManager;",null , null)
        classNode.fields.add(node)
        //classNode.fields.add()
    }

    private fun injectFieldProxy(classNode: ClassNode) {
        val node = FieldNode(ACC_PUBLIC + ACC_STATIC, "proxy", "LProxyConnection;",null , null)
        classNode.fields.add(node)
    }

    /*
    private fun injectInterface(classNode: ClassNode) {
        classNode.interfaces.add("com/p3achb0t/interfaces/IScriptManager")
    }*/

    private fun injectCustomClient(classNode: ClassNode) {

        for (method in classNode.methods) {
            if (method.name == "<init>") {
                method.desc = "(Ljava/lang/String;)V"
                val i: InsnList = method.instructions
                val last = i.last

                val ins = InsnList()
                ins.add(TypeInsnNode(NEW, "com/p3achb0t/client/injection/InstanceManager"))
                ins.add(InsnNode(DUP))
                ins.add(VarInsnNode(ALOAD, 0))
                ins.add(MethodInsnNode(INVOKESPECIAL, "com/p3achb0t/client/injection/InstanceManager", "<init>", "(Ljava/lang/Object;)V"))
                ins.add(FieldInsnNode(PUTSTATIC, "client", "script", "Lcom/p3achb0t/client/injection/InstanceManager;"))

                ins.add(TypeInsnNode(NEW, "ProxyConnection"))
                ins.add(InsnNode(DUP))
                ins.add(VarInsnNode(ALOAD, 1))
                ins.add(MethodInsnNode(INVOKESPECIAL, "ProxyConnection", "<init>", "(Ljava/lang/String;)V"))

                ins.add(FieldInsnNode(PUTSTATIC, "client", "proxy", "LProxyConnection;"))

                i.insert(last.previous, ins)
                method.maxStack += 6
                method.maxLocals += 1
            }
        }

        val getter = MethodNode(ACC_PUBLIC, "getManager", "()Lcom/p3achb0t/client/injection/InstanceManager;", null, null)
        val lli = getter.instructions
        lli.add(VarInsnNode(Opcodes.ALOAD, 0)) // maybe
        lli.add(FieldInsnNode(GETSTATIC, "client", "script","Lcom/p3achb0t/client/injection/InstanceManager;"))
        lli.add(InsnNode(ARETURN))
        getter.maxStack = 2
        getter.maxLocals = 2

        classNode.methods.add(ACC_PUBLIC, getter)
    }

    private fun injectSocket(classNode: ClassNode) {
        for (method in classNode.methods) {
            if (method.name == "run") {

                val i: InsnList = method.instructions

                for (insn in i) {
                    if (insn.opcode == NEW) {
                        if (insn is TypeInsnNode) {
                            if (insn.desc == "java/net/Socket") {
                                insn.desc = "ProxySocket"
                            }
                        }
                    }
                    if (insn is MethodInsnNode) {
                        val mnode = insn
                        if (mnode.owner == "java/net/Socket" && insn.opcode == INVOKESPECIAL) {
                            mnode.owner = "ProxySocket"
                            //println("#####################################################3")
                            //mnode.desc = "(Ljava/net/InetAddress;ILcom/p3achb0t/injection/Replace/ProxySocket;)V"
                            //val ins = InsnList()
                            //ins.add(FieldInsnNode(GETSTATIC, "client", "proxy","Lcom/p3achb0t/injection/Replace/ProxySocket;"))
                            //i.insert(insn.previous, ins)
                            //method.maxStack += 3
                        }
                    }
                }
            }
        }
    }

    // Source of this came from: https://github.com/Parabot/Parabot/blob/4ae861154c699055e244c037efe22d6ceb07ec2e/src/main/java/org/parabot/core/asm/adapters/AddInvokerAdapter.java#L49
    private fun injectInvoker(
            invokeData: InvokerData,
            classes: MutableMap<String, ClassNode>,
            analyserClass: String,
            runeStar: RuneStarAnalyzer?

    ){
        var isStatic = false
        val mArgsDescription = invokeData.argumentDescription

        val intoClazzNode = classes[runeStar?.analyzers?.get(analyserClass)?.name]
        val ownerClassNode = classes[invokeData.owner]
        println("Looking for ${invokeData.normalizedMethodName} in class: $analyserClass")
        val methodHook = runeStar?.analyzers?.get(analyserClass)?.methods?.find { it.method == invokeData.normalizedMethodName }
        println("MethodHook: $methodHook")

        val mn = ownerClassNode?.methods?.find { it.name == methodHook?.name }!!
        println("Signature: " + mn.signature + " desc" + mn.desc)

        val m = MethodNode(
                ACC_PUBLIC,
                invokeData.normalizedMethodName,
                mArgsDescription + invokeData.returnDescription,
                null,
                null
        )
        if (!invokeData.isInterface)
        {
            isStatic = (mn.access and ACC_STATIC) !== 0
            if (!Modifier.isPublic(mn.access))
            {
                if (Modifier.isPrivate(mn.access))
                {
                    mn.access = mn.access and (ACC_PRIVATE.inv())
                }
                if (Modifier.isProtected(mn.access))
                {
                    mn.access = mn.access and (ACC_PROTECTED.inv())
                }
                mn.access = mn.access or ACC_PUBLIC
                //mn.access = mn.access | ACC_SYNCHRONIZED;
            }
        }
        if(!isStatic || invokeData.isInterface){
            m.visitVarInsn(ALOAD,0)
        }
        if (invokeData.argumentDescription != "()")
        {
            val castArgs = if (invokeData.argsCheckCastDesc == null) null else Type.getArgumentTypes(invokeData.argsCheckCastDesc + "V")
            val methodArgs = Type.getArgumentTypes(invokeData.argumentDescription + "V")
            for (i in methodArgs.indices)
            {
                m.visitVarInsn(getLoadOpcode(methodArgs[i].descriptor), i + 1)
                if (castArgs != null && !castArgs[i]?.descriptor.equals(methodArgs[i].descriptor))
                {
                    var cast = methodArgs[i].descriptor
                    if (cast.startsWith("L"))
                    {
                        cast = cast.substring(1).replace(";", "")
                    }
                    m.visitTypeInsn(CHECKCAST, cast)
                }
            }
        }
        if (invokeData.isInterface)
        {
            m.visitMethodInsn(INVOKEINTERFACE, invokeData.instanceCast, invokeData.invokeMethodName, invokeData.argumentDescription+invokeData.returnDescription)
        }
        else
        {
            m.visitMethodInsn(if (isStatic) INVOKESTATIC else INVOKEVIRTUAL, intoClazzNode?.name, mn.name, mn.desc)
        }
        if (invokeData.returnDescription.contains("L"))
        {
            if (!invokeData.returnDescription.contains("["))
            {
                m.visitTypeInsn(CHECKCAST, invokeData.returnDescription
                        .replaceFirst(("L").toRegex(), "").replace((";").toRegex(), ""))
            }
            else
            {
                m.visitTypeInsn(CHECKCAST, invokeData.returnDescription)
            }
        }
        m.visitInsn(getReturnOpcode(invokeData.returnDescription))
        m.visitMaxs(0, 0)
        m.visitEnd()
        m.accept(classes[runeStar?.analyzers?.get(analyserClass)?.name])
//        classes[runeStar?.analyzers?.get(analyserClass)?.name]?.methods?.add(m)
    }

    private fun injectFieldGetter(
            getterData: GetterData,
            classes: MutableMap<String, ClassNode>,
            analyserClass: String,
            runeStar: RuneStarAnalyzer?
    ) {
        val normalizedFieldName = getterData.methodName
        val field = runeStar?.analyzers?.get(analyserClass)?.fields?.find { it.field == normalizedFieldName }
        val fieldOwner = field?.owner
        val fieldName = field?.name

        val fieldDescriptor = getterData.fieldDescription
        val returnFieldDescription =
                if (getterData.returnFieldDescription == "") getterData.fieldDescription else getterData.returnFieldDescription


        val signature = classes[fieldOwner]?.fields?.find { it.name == fieldName }?.signature
//        println("Class:$analyserClass Filed: $normalizedFieldName fieldOwner: $fieldOwner sig:$signature ReturnFieldDesc:$returnFieldDescription")
        val methodNode =
                MethodNode(ACC_PUBLIC, normalizedFieldName, "()$returnFieldDescription", signature, null)


        val isStatic = classes[fieldOwner]?.fields?.find { it.name == fieldName }?.access?.and(ACC_STATIC) != 0
        val fieldType = if (isStatic) GETSTATIC else GETFIELD
        if (!isStatic) {
            methodNode.visitVarInsn(ALOAD, 0)
        }
        methodNode.visitFieldInsn(fieldType, fieldOwner, fieldName, fieldDescriptor)
        val multiplier = field?.decoder
        if (multiplier != null && multiplier != 0L) {
            //println("Multiplier $multiplier ${field.decoder} ")
            if (field.descriptor == "J") {
                methodNode.visitLdcInsn(multiplier)
                methodNode.visitInsn(LMUL)
            } else {
                methodNode.visitLdcInsn(multiplier.toInt())
                methodNode.visitInsn(IMUL)
            }
        }

//        println(
//            "class:$fieldOwner normalName:$normalizedFieldName obsName:$fieldName type:$fieldDescriptor returnFieldDescription:$returnFieldDescription $fieldType $signature return: ${getReturnOpcode(
//                fieldDescriptor
//            )} Static:$isStatic"
//        )
        methodNode.visitInsn(getReturnOpcode(returnFieldDescription))

        if (multiplier != null) {
            methodNode.visitMaxs(5, 1)
        }else{
            methodNode.visitMaxs(3, 1)
        }
        methodNode.visitEnd()
        if(!returnFieldDescription.contains("null") && runeStar?.analyzers?.get(analyserClass)?.name in classes) {
//                println("${classes[runeStar?.analyzers?.get(analyserClass)?.name]} ${runeStar?.analyzers?.get(analyserClass)?.name}")
            methodNode.accept(classes[runeStar?.analyzers?.get(analyserClass)?.name])
        }else{
            //println("Error trying to insert $$normalizedFieldName. FieldDescriptor: $returnFieldDescription")
        }

    }
}