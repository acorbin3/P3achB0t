package com.p3achb0t.injection

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode


abstract class Transformer : Opcodes {
    val interfacePath = "com/p3achb0t/hook_interfaces"

    abstract fun transform(cn: ClassNode)
    abstract fun getName()

    fun getFieldNode(classes: Map<String, ClassNode>, owner: String, field_name: String): FieldNode? {
        val cn = classes[owner]
        for (fn in cn?.fields as List<FieldNode>) {
            if (fn.name == field_name) {
                return fn
            }
        }
        return null
    }
}