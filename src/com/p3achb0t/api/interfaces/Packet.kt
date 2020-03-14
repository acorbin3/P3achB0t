package com.p3achb0t.api.interfaces

interface Packet : Node {
    fun getArray(): ByteArray
    fun getIndex(): Int
}
