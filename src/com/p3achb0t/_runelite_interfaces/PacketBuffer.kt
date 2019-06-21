package com.p3achb0t._runelite_interfaces

interface PacketBuffer : Buffer {
    fun getBitIndex(): Int
    fun getIsaacCipher0(): IsaacCipher
}
