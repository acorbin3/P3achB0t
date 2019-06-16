package com.p3achb0t.hook_interfaces

interface ClassData : Node {
    fun getByteArray(): Array<Array<ByteArray>>
    fun getFields(): Any
    fun getMethods(): Any
}
