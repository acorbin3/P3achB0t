package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.tree.ClassNode

class NpcComposite : RSClasses {
    var actions = ArrayList<String>()
    var id = 0
    var name = ""

    override fun analyze(node: ClassNode, rsClassesMap: Map<String, RSClasses>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    constructor()
    constructor(fields: MutableMap<String, Field?>) : super() {
        if (fields["actions"]?.arrayData != null) {
            for (action in fields["actions"]?.arrayData!!)
                if (action is Field)
                    actions.add(action.resultValue)
        }

        id = fields["id"]?.resultValue?.toInt() ?: -1
        name = fields["name"]?.resultValue.toString()
    }
}