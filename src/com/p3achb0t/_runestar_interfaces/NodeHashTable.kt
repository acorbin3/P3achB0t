package com.p3achb0t._runestar_interfaces

interface NodeHashTable {
    fun getBuckets(): Array<Node>
    fun getCurrent(): Node
    fun getCurrentGet(): Node
    fun getIndex(): Int
    fun getSize(): Int
}
