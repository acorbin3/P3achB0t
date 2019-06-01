package com.p3achb0t.analyser

class RuneLiteJSONClasses {
    data class RuneLiteClass(
        val _class: String,
        val name: String,
        val _super: String,
        val access: Int,
        val interfaces: Array<String>,
        val fields: Array<RuneLiteField>,
        val methods: Array<RuneLiteMethod>,
        val constructors: Array<RuneLiteConstructor>
    )
}

data class RuneLiteConstructor(val access: Int, val descriptor: String)

data class RuneLiteMethod(
    val method: String,
    val owner: String,
    val name: String,
    val access: Int,
    val parameters: Array<String>,
    val descriptor: String
)

data class RuneLiteField(
    val field: String,
    val owner: String,
    val name: String,
    val access: Int,
    val descriptor: String
)
