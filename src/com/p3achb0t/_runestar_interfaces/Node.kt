package com.p3achb0t._runestar_interfaces

interface Node {
    fun getKey(): Long
    fun getNext(): Node
    fun getPrevious(): Node
}
