package com.p3achb0t.rsclasses

import org.objectweb.asm.tree.ClassNode

class Player : Actor {

    var composite: PlayerComposite = PlayerComposite()
    var hidden = ""
    var level = 0
    var model = ""
    var name = ""
    var overheadIcon = 0
    var skullIcon = ""
    var standingStill = ""
    var team = 0

    constructor()
    constructor(fields: MutableMap<String, Field?>) : super(fields) {
        composite = PlayerComposite(fields)
        hidden = fields["hidden"]?.resultValue.toString()
        level = fields["level"]?.resultValue?.toInt() ?: -1
        model = fields["model"]?.resultValue.toString()
        name = fields["name"]?.resultValue.toString()
        overheadIcon = fields["overheadIcon"]?.resultValue?.toInt() ?: -1
        skullIcon = fields["skullIcon"]?.resultValue.toString()
        standingStill = fields["standingStill"]?.resultValue.toString()
        team = fields["team"]?.resultValue?.toInt() ?: -1
    }

    companion object {
        const val deobName = "PLAYER"
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>) {
        val actor = rsClassesMap[Actor.deobName] as Actor
//        val isAbstract = (node.access and Opcodes.ACC_ABSTRACT) != 0
        if(actor.obsName == node.superName && node.fields.size > 5){
            println("Player: " + node.name)
            this.obsName = node.name
            this.found = true
//            println("   Number of Fields: " + node.fields.size)
//
//            println("abstract and: " + (node.access and Opcodes.ACC_ABSTRACT).toString())
//            println("   access abstract: $isAbstract")
//            println("   number of methods: " + node.methods.size)
//            println("   outerClass:\t" + node.outerClass)
//            println("   outerMethod:\t" + node.outerMethod)
//            println("   outerMethodDesc:\t" + node.outerMethodDesc)
//            println("   signature:\t" + node.signature)
//            println("   access:\t" + node.access)
//            println("   outerClass:\t" + node.)
//            println("   outerClass:\t" + node.outerClass)
//            println("   outerClass:\t" + node.outerClass)

        }
    }
}