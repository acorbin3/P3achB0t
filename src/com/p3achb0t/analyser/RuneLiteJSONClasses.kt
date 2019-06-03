package com.p3achb0t.analyser

class RuneLiteJSONClasses {
    data class ClassDefinition(
        val _class: String,
        val name: String,
        val _super: String,
        val access: Int,
        val interfaces: Array<String>,
        val fields: Array<FieldDefinition>,
        val methods: Array<MethodDefinition>,
        val constructors: Array<ConstructorDefinition>
    )
}

data class ConstructorDefinition(val access: Int, val descriptor: String)

data class MethodDefinition(
    val method: String,
    val owner: String,
    val name: String,
    val access: Int,
    val parameters: Array<String>,
    val descriptor: String
)

data class FieldDefinition(
    val field: String,
    val owner: String,
    val name: String,
    val access: Int,
    val descriptor: String,
    val decoder: Long = 0L
)
