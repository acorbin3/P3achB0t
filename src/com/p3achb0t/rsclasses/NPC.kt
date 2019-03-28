package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.Opcodes
import jdk.internal.org.objectweb.asm.tree.ClassNode

class NPC:RSClasses() {
    var composite: String = ""

    companion object {
        const val deobName = "NPC"
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>) {
        val actor = rsClassesMap[Actor.deobName] as Actor

        if(actor.name == node.superName && node.fields.size < 5){
            println("NPC: " + node.name)
            this.name = node.name
//            this.found = true

//            println("   outerClass:\t" + node.)
//            println("   outerClass:\t" + node.outerClass)
//            println("   outerClass:\t" + node.outerClass)

        }
    }
}