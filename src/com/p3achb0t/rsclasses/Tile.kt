package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.tree.ClassNode

class Tile : RSClasses {
    var boundary = ""
    var floor = ""
    var itemLayer = ""
    var objects = ""
    var plane = 0
    var wall = ""
    var x = 0
    var y = 0

    constructor()
    constructor(fields: MutableMap<String, Field?>) : super() {
        boundary = fields["boundary"]?.resultValue.toString()
        floor = fields["floor"]?.resultValue.toString()
        itemLayer = fields["itemLayer"]?.resultValue.toString()
        objects = fields["objects"]?.resultValue.toString()
        plane = fields["plane"]?.resultValue?.toInt() ?: -1
        wall = fields["wall"]?.resultValue.toString()
        x = fields["x"]?.resultValue?.toInt() ?: -1
        y = fields["y"]?.resultValue?.toInt() ?: -1
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String, RSClasses>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}