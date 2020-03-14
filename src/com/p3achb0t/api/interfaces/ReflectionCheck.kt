package com.p3achb0t.api.interfaces

interface ReflectionCheck : Node {
    fun getArguments(): Array<Array<ByteArray>>
    fun getCreationErrors(): IntArray
    fun getFields(): Any
    fun getId(): Int
    fun getIntReplaceValues(): IntArray
    fun getMethods(): Any
    fun getOperations(): IntArray
    fun getSize(): Int
}
