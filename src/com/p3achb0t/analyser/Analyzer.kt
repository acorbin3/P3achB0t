package com.p3achb0t.analyser

import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.injection.class_generation.cleanType
import com.p3achb0t.injection.class_generation.isBaseType
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.interfaces.ScriptManager
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
                injectField(classes[clazzData.name]!!)
                AsmUtil.addInterface(classes[clazzData.name]!!,"com/p3achb0t/interfaces/IScriptManager")
                injectCustomClient(classes[clazzData.name]!!)
                AsmUtil.addStaticMethod(classes[clazzData.name]!!, "getKeyboard", "()Lcom/p3achb0t/client/interfaces/io/Keyboard;", "ar", "c", "Lar;")
                AsmUtil.addStaticMethod(classes[clazzData.name]!!, "getMouse", "()Lcom/p3achb0t/client/interfaces/io/Mouse;", "bk", "l", "Lbk;")
            }



            if (clazzData.`class` == "Canvas") {

                injectCanvas(classes[clazzData.name]!!)
                println("${clazzData.name} : ${clazzData.`super`} ")
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
                // add in MouseHandler
                // <getter fieldDesc="I" fieldName="s" fieldOwner="bu" methodDesc="I" methodName="getY" multiplier="476322543"/>
                // <getter fieldDesc="I" fieldName="b" fieldOwner="bu" methodDesc="I" methodName="getX" multiplier="805078735"/>
                // {"field":"MouseHandler_x","owner":"bk","name":"h","access":9,"descriptor":"I","decoder":-1689480427},
                // {"field":"MouseHandler_x0","owner":"bk","name":"v","access":73,"descriptor":"I","decoder":1059127459},
                // {"field":"MouseHandler_y","owner":"bk","name":"x","access":9,"descriptor":"I","decoder":-455222981},
                // {"field":"MouseHandler_y0","owner":"bk","name":"d","access":73,"descriptor":"I","decoder":-871019493},
                AsmUtil.setSuper(classes[clazzData.name]!!, "java/lang/Object", "com/p3achb0t/client/interfaces/io/Mouse")
                AsmUtil.addMethod(classes[clazzData.name]!!, "getX", "()I", "bk", "h", "I", -1689480427)
                AsmUtil.addMethod(classes[clazzData.name]!!, "getY", "()I", "bk", "x", "I",-455222981)
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseClicked", "_mouseClicked")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseDragged", "_mouseDragged")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseEntered", "_mouseEntered")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseExited", "_mouseExited")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseMoved", "_mouseMoved")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mousePressed", "_mousePressed")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseReleased", "_mouseReleased")
                AsmUtil.renameMethod(classes[clazzData.name]!!, "mouseWheelMoved", "_mouseWheelMoved")
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

    private fun injectField(classNode: ClassNode) {
        val node = FieldNode(ACC_PUBLIC + ACC_STATIC, "script", "Lcom/p3achb0t/interfaces/ScriptManager;",null , null)
        classNode.fields.add(node)
        //classNode.fields.add()
    }

    private fun injectInterface(classNode: ClassNode) {
        classNode.interfaces.add("com/p3achb0t/interfaces/IScriptManager");
    }

    private fun injectCustomClient(classNode: ClassNode) {

        for (method in classNode.methods) {
            if (method.name == "<init>") {
                val i: InsnList = method.instructions
                val last = i.last

                val ins = InsnList()
                ins.add(VarInsnNode(Opcodes.ALOAD, 0))
                ins.add(TypeInsnNode(NEW, "com/p3achb0t/interfaces/ScriptManager"))
                ins.add(InsnNode(DUP))
                ins.add(VarInsnNode(Opcodes.ALOAD, 0))
                //ins.add(VarInsnNode(Opcodes.ALOAD, 0))
                ins.add(MethodInsnNode(INVOKESPECIAL, "com/p3achb0t/interfaces/ScriptManager", "<init>", "(Lcom/p3achb0t/_runestar_interfaces/Client;)V"))

                ins.add(FieldInsnNode(PUTSTATIC, "client", "script", "Lcom/p3achb0t/interfaces/ScriptManager;"))
                //ins.add(FieldInsnNode(GETSTATIC, "client", "script","Lcom/p3achb0t/interfaces/ScriptManager;"))
                i.insert(last.previous, ins)
                method.maxStack += 6
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
        //println("Class:$analyserClass Filed: $normalizedFieldName fieldOwner: $fieldOwner sig:$signature ReturnFieldDesc:$returnFieldDescription")
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

        //println(
        //    "class:$fieldOwner normalName:$normalizedFieldName obsName:$fieldName type:$fieldDescriptor returnFieldDescription:$returnFieldDescription $fieldType $signature return: ${getReturnOpcode(
        //        fieldDescriptor
        //    )} Static:$isStatic"
        //)
        methodNode.visitInsn(getReturnOpcode(returnFieldDescription))

        if (multiplier != null) {
            methodNode.visitMaxs(5, 1)
        }else{
            methodNode.visitMaxs(3, 1)
        }
        methodNode.visitEnd()
        if(!returnFieldDescription.contains("null")) {
                println("${classes[runeStar?.analyzers?.get(analyserClass)?.name]} ${runeStar?.analyzers?.get(analyserClass)?.name}")
                methodNode.accept(classes[runeStar?.analyzers?.get(analyserClass)?.name])
        }else{
            //println("Error trying to insert $$normalizedFieldName. FieldDescriptor: $returnFieldDescription")
        }

    }
}