package com.p3achb0t.hook_interfaces

interface HashTable {
    fun getBuckets(): Array<Node>
    fun getCurrent(): Node
    fun getHead(): Node
    fun getIndex(): Int
    fun getSize(): Int
}
