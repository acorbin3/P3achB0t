package com.p3achb0t.analyser

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.ACC_PUBLIC
import org.objectweb.asm.tree.*

class AsmUtil {
    companion object {

        fun addInterface(classNode: ClassNode, interfaceName: String) {
            classNode.interfaces.add(interfaceName)
        }

        fun setSuper(classNode: ClassNode, superClass: String, newSuperClass: String) {
            val mli = classNode.methods.listIterator()
            while (mli.hasNext()) {
                val mn = mli.next() as MethodNode
                val ili = mn.instructions.iterator()
                while (ili.hasNext()) {
                    val ain = ili.next() as AbstractInsnNode
                    if (ain.opcode == Opcodes.INVOKESPECIAL) {
                        val min = ain as MethodInsnNode
                        if (min.owner == superClass) {
                            min.owner = newSuperClass
                            break
                        }
                    }
                }
            }
            println("${newSuperClass}")
            classNode.superName = newSuperClass
        }

        fun renameMethod(classNode: ClassNode, name: String, newName: String) {
            val mli = classNode.methods.listIterator()
            while (mli.hasNext()) {
                val mn = mli.next() as MethodNode
                if (mn.name == name) {
                    mn.name = newName
                }
            }
        }

        fun addStaticMethod(classNode: ClassNode, name: String, type: String, fieldOwner: String, fieldName: String, fieldDescriptor: String) {
            val node = MethodNode(ACC_PUBLIC, name, type, null, null)
            val insn = node.instructions
            insn.add(VarInsnNode(Opcodes.ALOAD, 0))
            insn.add(FieldInsnNode(Opcodes.GETSTATIC, fieldOwner, fieldName,fieldDescriptor))
            insn.add(InsnNode(Opcodes.ARETURN))
            node.maxStack = 2
            node.maxLocals = 2
            classNode.methods.add(node)
        }

        fun addMethod(classNode: ClassNode, name: String, type: String, fieldOwner: String, fieldName: String, fieldDescriptor: String, multiplier: Int) {
            val node = MethodNode(ACC_PUBLIC, name, type, null, null)
            val insn = node.instructions
            insn.add(VarInsnNode(Opcodes.ALOAD, 0))
            insn.add(FieldInsnNode(Opcodes.GETSTATIC, fieldOwner, fieldName,fieldDescriptor))
            insn.add(LdcInsnNode(multiplier));
            insn.add(InsnNode(Opcodes.IMUL));
            insn.add(InsnNode(Opcodes.IRETURN))
            node.maxStack = 3
            node.maxLocals = 2
            classNode.methods.add(node)
        }

    }
}