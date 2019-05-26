package com.p3achb0t.hook_interfaces

interface Node {
    fun get_id(): Int
    fun get_next(): Node
    fun get_previous(): Node
}
