package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.Opcodes
import jdk.internal.org.objectweb.asm.tree.ClassNode

//data class Renderable(val name: String, val modelHeight: String)
class Renderable:RSClasses() {
    var modelHeight: String = ""
    companion object {
        const val deobName = "RENDERABLE"
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>) {
        //DONE 3/25/2019 Look for Renderable (is a CacheNode)
        val cacheNode = (rsClassesMap[CacheNode.deobName] as CacheNode)
        if(cacheNode.found && cacheNode.name == node.superName && node.fields.size == 2){
            println("Renderable class " + node.name)
            println("   Number of Fields: " + node.fields.size)
            println("   access abstract: " + (node.access == Opcodes.ACC_ABSTRACT))
            println("   number of methods: " + node.methods.size)
            val modelHeightField = node.fields.find { it.desc == "I" }
            this.name = node.name
            this.modelHeight = modelHeightField?.name!!
            println("   Model Height: " + modelHeightField.name)
            this.found = true
            

        }
    }
}