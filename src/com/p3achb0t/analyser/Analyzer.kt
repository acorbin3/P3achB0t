package com.p3achb0t.analyser

import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.injection.class_generation.cleanType
import com.p3achb0t.injection.class_generation.isBaseType
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.configs.Constants.Companion.USER_DIR
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream


class Analyser{

    val classes: MutableMap<String, ClassNode> = mutableMapOf()

    fun createInjectedJar(jar: JarFile, runeStar: RuneStarAnalyzer?) {
        val enumeration = jar.entries()
        while(enumeration.hasMoreElements()){
            val entry = enumeration.nextElement()
            if(entry.name.endsWith(".class")){
                val classReader = ClassReader(jar.getInputStream(entry))
                val classNode = ClassNode()
                classReader.accept(classNode, 0)
//                if (classNode.interfaces.size > 0) {
//
////                    println("${classNode.name} Interfaces")
//                }
//                for (_interface in classNode.interfaces) {
////                    println("\t $_interface")
//                }
                classes[classNode.name] = classNode


                //Checking for SceneRespondRequest
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

    private fun injectJARWithInterfaces(classes: MutableMap<String, ClassNode>, runeStar: RuneStarAnalyzer?) {
        val classPath = "com/p3achb0t/_runestar_interfaces"
        runeStar?.classRefObs?.forEach { obsClass, clazzData ->

            if(clazzData.`class` == "Client") {
                injectScriptManagerField(classes[clazzData.name]!!)
                injectFieldProxy(classes[clazzData.name]!!)
                AsmUtil.addInterface(classes[clazzData.name]!!,"com/p3achb0t/interfaces/IScriptManager")
                injectCustomClient(classes[clazzData.name]!!)
                clazzData.fields.forEach{
                    if(it.field .contains("MouseHandler_instance")){
                        println("Found field MouseHandler_instance ${it.toString}")
                        AsmUtil.addStaticMethod(
                                classes[clazzData.name]!!,
                                "getMouse",
                                "()Lcom/p3achb0t/client/interfaces/io/Mouse;",
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
                                "()Lcom/p3achb0t/client/interfaces/io/Keyboard;",
                                it.owner,
                                it.name,
                                "L${it.owner};"
                        ) // KeyHandler_instance
                    }
                }


            }

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
                    injectMethod(method, classes, clazzData.`class`, runeStar)
            }

            if (clazzData.`class` == "KeyHandler") {
                AsmUtil.setSuper(classes[clazzData.name]!!,"java/lang/Object","com/p3achb0t/client/interfaces/io/Keyboard")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "keyPressed", "_keyPressed");
                AsmUtil.renameMethod(classes[clazzData.name]!!, "keyReleased", "_keyReleased");
                AsmUtil.renameMethod(classes[clazzData.name]!!, "keyTyped", "_keyTyped");
            }

            if (clazzData.`class` == "MouseHandler") {
                AsmUtil.setSuper(classes[clazzData.name]!!, "java/lang/Object", "com/p3achb0t/client/interfaces/io/Mouse")
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


        val path = System.getProperty("user.dir")
        val out = JarOutputStream(FileOutputStream(File("$path/${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}")))
        for (classNode in classes.values) {
            val cw = ClassWriter(0)
            classNode.accept(cw)
            out.putNextEntry(JarEntry(classNode.name + ".class"))
            out.write(cw.toByteArray())
            out.closeEntry()
        }

        out.putNextEntry(JarEntry("ProxySocket.class"))
        out.write(putClasses("$USER_DIR/src/ProxySocket.class"))
        out.closeEntry()

        out.putNextEntry(JarEntry("ProxyConnection.class"))
        out.write(putClasses("$USER_DIR/src/ProxyConnection.class"))

        out.closeEntry()

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
            'I', 'B', 'Z', 'S', 'C' -> if (opcodeType == OpcodeType.LOAD) ILOAD else IRETURN
            else -> if (opcodeType == OpcodeType.LOAD) ALOAD else ARETURN
        }
    }

    private fun injectCanvas(classNode: ClassNode) {
        classNode.superName = "com/p3achb0t/injection/Replace/RsCanvas"
        for (method in classNode.methods) {
            if (method.name == "<init>") {
                val i: InsnList = method.instructions
                for (insn in i) {
                    if (insn.opcode == Opcodes.INVOKESPECIAL) {
                        if (insn is MethodInsnNode) {
                            val mnode = insn
                            mnode.owner = "com/p3achb0t/injection/Replace/RsCanvas"
                            mnode.desc = "(Lcom/p3achb0t/interfaces/ScriptManager;)V"
                            val ins = InsnList()
                            ins.add(FieldInsnNode(GETSTATIC, "client", "script","Lcom/p3achb0t/interfaces/ScriptManager;"))
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
                                il.add(FieldInsnNode(GETSTATIC, "client", "script", "Lcom/p3achb0t/interfaces/ScriptManager;"))
                                il.add(MethodInsnNode(INVOKEVIRTUAL, "com/p3achb0t/interfaces/ScriptManager", "loop", "()V"))
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
        val file = File(name)
        return file.readBytes()
    }

    private fun injectScriptManagerField(classNode: ClassNode) {
        val node = FieldNode(ACC_PUBLIC + ACC_STATIC, "script", "Lcom/p3achb0t/interfaces/ScriptManager;",null , null)
        classNode.fields.add(node)
        //classNode.fields.add()
    }

    private fun injectFieldProxy(classNode: ClassNode) {
        val node = FieldNode(ACC_PUBLIC + ACC_STATIC, "proxy", "LProxyConnection;",null , null)
        classNode.fields.add(node)
    }

    private fun injectInterface(classNode: ClassNode) {
        classNode.interfaces.add("com/p3achb0t/interfaces/IScriptManager");
    }

    private fun injectCustomClient(classNode: ClassNode) {

        for (method in classNode.methods) {
            if (method.name == "<init>") {
                method.desc = "(Ljava/lang/String;)V"
                val i: InsnList = method.instructions
                val last = i.last

                val ins = InsnList()
                ins.add(TypeInsnNode(NEW, "com/p3achb0t/interfaces/ScriptManager"))
                ins.add(InsnNode(DUP))
                ins.add(VarInsnNode(ALOAD, 0))
                ins.add(MethodInsnNode(INVOKESPECIAL, "com/p3achb0t/interfaces/ScriptManager", "<init>", "(Ljava/lang/Object;)V"))
                ins.add(FieldInsnNode(PUTSTATIC, "client", "script", "Lcom/p3achb0t/interfaces/ScriptManager;"))

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

        val getter = MethodNode(ACC_PUBLIC, "getManager", "()Lcom/p3achb0t/interfaces/ScriptManager;", null, null)
        val lli = getter.instructions
        lli.add(VarInsnNode(Opcodes.ALOAD, 0)) // maybe
        lli.add(FieldInsnNode(GETSTATIC, "client", "script","Lcom/p3achb0t/interfaces/ScriptManager;"))
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

    private fun injectMethod(
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
        println("Class:$analyserClass Filed: $normalizedFieldName fieldOwner: $fieldOwner sig:$signature ReturnFieldDesc:$returnFieldDescription")
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

        println(
            "class:$fieldOwner normalName:$normalizedFieldName obsName:$fieldName type:$fieldDescriptor returnFieldDescription:$returnFieldDescription $fieldType $signature return: ${getReturnOpcode(
                fieldDescriptor
            )} Static:$isStatic"
        )
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