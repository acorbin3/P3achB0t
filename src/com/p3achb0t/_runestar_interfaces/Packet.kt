package com.p3achb0t._runestar_interfaces

interface Packet : Node {
    fun getArray(): ByteArray
    fun getIndex(): Int
}
