package com.p3achb0t.analyser

class RuneLiteJSONClasses {
    data class ClassDefinition(
        var _class: String = "",
        var name: String = "",
        var _super: String = "",
        var access: Int = 0,
        var interfaces: ArrayList<String> = arrayListOf(),
        var fields: ArrayList<FieldDefinition> = arrayListOf(),
        var methods: ArrayList<MethodDefinition> = arrayListOf(),
        var constructors: ArrayList<ConstructorDefinition> = arrayListOf()
    )

    data class ConstructorDefinition(var access: Int = 0, var descriptor: String = "")

    data class MethodDefinition(
        var method: String = "",
        var owner: String = "",
        var name: String = "",
        var access: Int = 0,
        var parameters: ArrayList<String> = arrayListOf(),
        var descriptor: String = ""
    )

    enum class DecoderType { LONG, INTEGER }
    data class FieldDefinition(
        var field: String = "",
        var owner: String = "",
        var name: String = "",
        var access: Int = 0,
        var descriptor: String = "",
        var decoder: Long = 0L,
        var decoderType: DecoderType = DecoderType.INTEGER
    )
}


