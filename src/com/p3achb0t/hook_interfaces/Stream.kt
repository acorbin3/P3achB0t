package com.p3achb0t.hook_interfaces

interface Stream : Node {
    fun getOffset(): Int
    fun getPayload(): ByteArray
}
