package com.p3achb0t.rsclasses

import org.objectweb.asm.tree.ClassNode

class PlayerComposite : RSClasses {
    var animatedModelID = ""
    var appearance = ""
    var bodyColors = ""
    var female = ""
    var npcID = 0
    var staticModelID = ""

    constructor()
    constructor(fields: MutableMap<String, Field?>) : super() {
        animatedModelID = fields["animatedModelID"]?.resultValue.toString()
        appearance = fields["appearance"]?.resultValue.toString()
        bodyColors = fields["bodyColors"]?.resultValue.toString()
        female = fields["female"]?.resultValue.toString()
        npcID = fields["npcID"]?.resultValue?.toInt() ?: -1
        staticModelID = fields["staticModelID"]?.resultValue.toString()
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String, RSClasses>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}