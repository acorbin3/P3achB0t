package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.tree.ClassNode

class ObjectComposite : RSClasses {
    var actions = ""
    var name = ""

    constructor()
    constructor(fields: MutableMap<String, Field>) : super() {
        actions = fields["actions"]?.resultValue.toString()
        name = fields["name"]?.resultValue.toString()
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String, RSClasses>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}