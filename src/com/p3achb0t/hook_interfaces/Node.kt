package com.p3achb0t.hook_interfaces

interface Node {
    fun getId(): Long
    fun getNext(): Node
    fun getPrevious(): Node
}
