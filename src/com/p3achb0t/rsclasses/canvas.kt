package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.Opcodes
import jdk.internal.org.objectweb.asm.tree.ClassNode

class Canvas:RSClasses() {
    var name: String = ""
    companion object {
        const val deobName = "Canvas"
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>) {
        if(node.superName == "java/awt/Canvas"){
            this.name = node.name
            this.found = true
        }
    }
}