package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.tree.ClassNode

class HashTable : RSClasses {
    var buckets = ""
    var current = ""
    var head = ""
    var index = ""
    var size = ""

    constructor()
    constructor(fields: MutableMap<String, Field>) : super() {
        buckets = fields["buckets"]?.resultValue.toString()
        current = fields["current"]?.resultValue.toString()
        head = fields["head"]?.resultValue.toString()
        index = fields["index"]?.resultValue.toString()
        size = fields["size"]?.resultValue.toString()
    }

    override fun analyze(node: ClassNode, rsClassesMap: Map<String, RSClasses>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}