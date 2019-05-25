package com.p3achb0t.hook_interfaces

interface MessageNode : CacheNode {
    fun get_clan(): Any
    fun get_nameComposite(): Any
    fun get_sender(): Any
    fun get_text(): Any
    fun get_time(): Int
    fun get_type(): Int
}
