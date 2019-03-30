package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.tree.ClassNode

class Canvas:RSClasses() {
    companion object {
        const val deobName = "Canvas"
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>) {
        if(node.superName == "java/awt/Canvas"){
            this.obsName = node.name
            this.found = true
        }
    }
}