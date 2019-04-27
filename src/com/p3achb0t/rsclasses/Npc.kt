package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.tree.ClassNode

class Npc : Actor {
    var composite: NpcComposite? = null

    constructor()
    constructor(fields: MutableMap<String, Field?>) : super(fields) {
        composite = NpcComposite(fields)
    }

    companion object {
        const val deobName = "Npc"
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>) {
        val actor = rsClassesMap[Actor.deobName] as Actor

        if(actor.obsName == node.superName && node.fields.size < 5){
            println("Npc: " + node.name)
            this.obsName = node.name
//            this.found = true

//            println("   outerClass:\t" + node.)
//            println("   outerClass:\t" + node.outerClass)
//            println("   outerClass:\t" + node.outerClass)

        }
    }
}