package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.Opcodes
import jdk.internal.org.objectweb.asm.tree.ClassNode
import mu.KLogger
import mu.KotlinLogging

abstract class RSClasses{
    class Field{
        var fieldName: String = ""
        var obsName: String = ""
        var fieldTypeObsName: String = ""
        var fieldTypeName: String = ""
        var modifier: Long = 0
        var classNode: ClassNode? = null

        override fun toString(): String {
            return "$fieldName -> ($fieldTypeObsName)$obsName Mod: $modifier"
        }
    }
    var found: Boolean = false
    var obsName: String = ""

    var fields = mutableMapOf<String,Field>()// Key = obfuscated, value is the normalized obsName
    lateinit var node: ClassNode
    protected val log: KLogger = KotlinLogging.logger { this.javaClass }
    abstract fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>)
    fun displayNode(){

        if(::node.isInitialized){
            val isAbstract = (node.access and Opcodes.ACC_ABSTRACT) != 0
            println("Node Name: " + node.name)
            println("   Number of Fields: " + node.fields.size)
            println("   abstract and: " + (node.access and Opcodes.ACC_ABSTRACT).toString())
            println("   access abstract: $isAbstract")
            println("   number of methods: " + node.methods.size)
            println("   outerClass:\t" + node.outerClass)
            println("   outerMethod:\t" + node.outerMethod)
            println("   outerMethodDesc:\t" + node.outerMethodDesc)
            println("   signature:\t" + node.signature)
            println("   access:\t" + node.access)
        }
    }
    fun displayNode(node:ClassNode){
        val isAbstract = (node.access and Opcodes.ACC_ABSTRACT) != 0
        val isPublic = (node.access and Opcodes.ACC_PUBLIC) != 0
        val isSuper = (node.access and Opcodes.ACC_SUPER) != 0
        val isFinal = (node.access and Opcodes.ACC_FINAL) != 0
        println("Node Name: " + node.name)
        println("   Number of Fields: " + node.fields.size)
        println("   abstract and: " + (node.access and Opcodes.ACC_ABSTRACT).toString())
        println("   access abstract: $isAbstract")
        println("   access public: $isPublic")
        println("   access super: $isSuper")
        println("   access final: $isFinal")

        println("   number of methods: " + node.methods.size)
        println("   outerClass:\t" + node.outerClass)
        println("   outerMethod:\t" + node.outerMethod)
        println("   outerMethodDesc:\t" + node.outerMethodDesc)
        println("   signature:\t" + node.signature)
        println("   access:\t" + node.access)
    }
}