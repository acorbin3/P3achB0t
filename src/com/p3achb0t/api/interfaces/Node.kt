package com.p3achb0t.api.interfaces

interface Node {
    fun getKey(): Long
    fun getNext(): Node
    fun getPrevious(): Node
}
