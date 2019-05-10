package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.tree.ClassNode

class GameObject : RSClasses {
    var flags = 0
    var height = ""
    var id = 0
    var offsetX = 0
    var offsetY = 0
    var orientation = 0
    var plane = 0
    var relativeX = 0
    var relativeY = ""
    var renderable = ""
    var x = 0
    var y = ""

    constructor()
    constructor(fields: MutableMap<String, Field?>) : super() {
        flags = fields["flags"]?.resultValue?.toInt() ?: -1
        height = fields["height"]?.resultValue.toString()
        id = fields["id"]?.resultValue?.toInt() ?: -1
        offsetX = fields["offsetX"]?.resultValue?.toInt() ?: -1
        offsetY = fields["offsetY"]?.resultValue?.toInt() ?: -1
        orientation = fields["orientation"]?.resultValue?.toInt() ?: -1
        plane = fields["plane"]?.resultValue?.toInt() ?: -1
        relativeX = fields["relativeX"]?.resultValue?.toInt() ?: -1
        relativeY = fields["relativeY"]?.resultValue.toString()
        renderable = fields["renderable"]?.resultValue.toString()
        x = fields["x"]?.resultValue?.toInt() ?: -1
        y = fields["y"]?.resultValue.toString()
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String, RSClasses>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}