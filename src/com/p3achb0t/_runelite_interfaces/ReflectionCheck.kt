package com.p3achb0t._runelite_interfaces

interface ReflectionCheck : Node {
    fun getArguments(): Array<Array<Array<Byte>>>
    fun getCreationErrors(): Array<Int>
    fun getFields(): Any
    fun getId(): Int
    fun getIntReplaceValues(): Array<Int>
    fun getMethods(): Any
    fun getOperations(): Array<Int>
    fun getSize(): Int
}
