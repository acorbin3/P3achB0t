package com.p3achb0t.rsclasses

import org.objectweb.asm.Opcodes.PUTSTATIC
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.IntInsnNode
import java.lang.reflect.Modifier

class VarpBit:RSClasses() {
    companion object {
        const val deobName = "VarpBit"
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String, RSClasses>) {

//        node.methods.filter { it.name == "<clinit>"}.firstOrNull()?.instructions?.iterator().
        if (node.interfaces.isEmpty()
            && node.fields.filter { !Modifier.isStatic(it.access) }.isEmpty()
            && node.methods.filter { !Modifier.isStatic(it.access) && it.name != "<init>" }.isEmpty()
        //&& node.fields.filter{ Modifier.isStatic(it.access)}.count{ Type.getObjectType(it.name) == Type.getObjectType(IntArray::class.simpleName) } >= 3
        ) {
            println("Potential VarpBit: ${node.name}.")
        }


        if (node.name == "client") {
            return
        }

        if (node.fields.size == 0) {
            val clint = node.methods.filter { it.name == "<clinit>" }

            if (clint.size == 1) {
//                clint[0].
//                println("Potential VarpBit: ${node.name}")
                clint[0].instructions.iterator().forEach {
                    if (it is FieldInsnNode) {
                        if (it.opcode == PUTSTATIC && it.desc == "[I") {
                            val push = it.previous.previous
                            if (push is IntInsnNode && push.operand == 4000) {
                                this.obsName = node.name
                                this.found = true
                                println("VarpBit: " + node.name)
                            }
                        }
                    }
                }
            }

        }
    }
}