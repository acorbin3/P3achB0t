package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.Opcodes
import jdk.internal.org.objectweb.asm.tree.ClassNode
import mu.KLogger
import mu.KotlinLogging
import java.io.Serializable

abstract class RSClasses{
    data class Field(var resultValue: String = "") : Serializable {
        var fieldName: String = ""
        var obsName: String = ""
        var fieldTypeObsName: String = ""
        var fieldTypeName: String = ""
        var modifier: Long = 0
        var classNode: ClassNode? = null
        var fields = mutableMapOf<String, Field?>()
        var isArray = false
        var arrayData: ArrayList<Any>? = ArrayList()

        override fun toString(): String {
            var res = ""
            for (field in fields.toSortedMap()) {
                if (!field.value?.isArray!!) {
                    res += "\n\t" + field.key + "->>" + field.value?.resultValue
                } else {
                    res += "\n\t" + field.key + "->> ["
                    if (field.value != null && field.value?.arrayData != null) {
                        for (item in field.value!!.arrayData!!) {
                            if (item is Field)
                                res += "$item,"
                        }
                    }
                    res += "]"
                }
            }
            return "$fieldName -> ($fieldTypeObsName)$obsName Mod: $modifier $res"
        }
    }
    var found: Boolean = false
    var obsName: String = ""
    var className: String = ""

    var fields = mutableMapOf<String, Field>()// Key = obfuscated, resultValue is the normalized obsName
    var normalizedFields =
        mutableMapOf<String, Field>()// Key = normalized parentId, resultValue is the obfuscated obsName
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