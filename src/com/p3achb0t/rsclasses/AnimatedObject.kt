package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.Opcodes
import jdk.internal.org.objectweb.asm.tree.ClassNode

class AnimatedObject:RSClasses() {

//    AnimatedObject.animationDelay ci j 59357379
//    AnimatedObject.animationFrame ci z 1554944269
//    AnimatedObject.clickType ci h 113466573
//    AnimatedObject.id ci n 1215306485
//    AnimatedObject.orientation ci l -16922329
//    AnimatedObject.plane ci g -727404899
//    AnimatedObject.sequence ci c
//    AnimatedObject.x ci b -1146750321
//    AnimatedObject.y ci a -656789023
    companion object {
        const val deobName = "AnimatedObject"
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>) {
        val renderable = rsClassesMap[Renderable.deobName] as Renderable
        val isPublic = (node.access and Opcodes.ACC_PUBLIC) != 0
        val isSuper = (node.access and Opcodes.ACC_SUPER) != 0
        val isFinal = (node.access and Opcodes.ACC_FINAL) != 0
        val intCount = node.fields.count { it.desc.contains("I") }
        if(renderable.obsName == node.superName && intCount == 8 && isPublic && isSuper && !isFinal ){
            println("AnimatiedObject: " + node.name)
            this.obsName = node.name
            this.found = true

        }
    }
}