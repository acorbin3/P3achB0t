package com.p3achb0t.api.interfaces

interface PacketBit : Packet {
    fun getBitIndex(): Int
    fun getIsaac0(): Isaac
}
