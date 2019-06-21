package com.p3achb0t._runelite_interfaces

interface Node {
    fun getKey(): Long
    fun getNext(): Node
    fun getPrevious(): Node
}
