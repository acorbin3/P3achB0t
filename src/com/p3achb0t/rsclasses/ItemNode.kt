package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.tree.ClassNode

class ItemNode : RSClasses {
    var ids = ""
    var stackSizes = ""

    constructor()
    constructor(fields: MutableMap<String, Field>) : super() {
        ids = fields["ids"]?.resultValue.toString()
        stackSizes = fields["stackSizes"]?.resultValue.toString()
    }

    override fun analyze(node: ClassNode, rsClassesMap: Map<String, RSClasses>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}