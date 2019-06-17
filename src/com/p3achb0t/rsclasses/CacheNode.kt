package com.p3achb0t.rsclasses

import org.objectweb.asm.tree.ClassNode

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
            val ownType = node.fields.count { it.desc.contains("L" + node.name) }

            if (longCount == 1  && ownType == 2 && node.superName != "java/lang/Object") {
                println("CacheNode Class: " + node.name + " " + node.superName)
                val idField = node.fields.find { it.desc == "J" }
                //TODO - Figure out how to identify next and previous
                println("   Did not identify next or previous")
//                        val prevField = node.fields.find { it.access != Opcodes.ACC_PUBLIC }
//                        val nextField = node.fields.find { it.access == Opcodes.ACC_PUBLIC }
//                        println("   Field ID: " + idField?.obsName)
//                        println("   Field Next: " + nextField?.obsName)
//                        println("   Field Previous: " + prevField?.obsName)
                this.obsName = node.name
                this.idName = idField?.name!!
//                this.nextName = nextField?.obsName!!
//                this.prevName = prevField?.obsName!!
                this.found = true
            }
        }
    }
}