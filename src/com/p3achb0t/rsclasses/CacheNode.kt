package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.Opcodes
import jdk.internal.org.objectweb.asm.tree.ClassNode

class CacheNode:RSClasses() {
    var idName: String = ""
    var nextName: String = ""
    var prevName: String = ""
    companion object {
        const val deobName = "CACHE_NODE"
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>) {
        if (node.fields?.size == 3) {
            val longCount = node.fields.count { it.desc.contains("J") }
            val ownType = node.fields.count { it.desc.contains("L" + node?.name) }

            if (longCount == 1  && ownType == 2 && node.superName != "java/lang/Object") {
                println("CacheNode Class: " + node.name + " " + node.superName)
                val idField = node.fields.find { it.desc == "J" }
                //TODO - Figure out how to identify next and previous
                println("   Did not identify next or previous")
//                        val prevField = node.fields.find { it.access != Opcodes.ACC_PUBLIC }
//                        val nextField = node.fields.find { it.access == Opcodes.ACC_PUBLIC }
//                        println("   Field ID: " + idField?.name)
//                        println("   Field Next: " + nextField?.name)
//                        println("   Field Previous: " + prevField?.name)
                this.name = node.name
                this.idName = idField?.name!!
//                this.nextName = nextField?.name!!
//                this.prevName = prevField?.name!!
                this.found = true
            }
        }
    }
}