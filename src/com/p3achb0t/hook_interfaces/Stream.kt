package com.p3achb0t.hook_interfaces

interface Stream : Node {
    fun get_offset(): Int
    fun get_payload(): Any
}
