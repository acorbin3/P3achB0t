package com.p3achb0t.api.interfaces

interface PacketBitNode : Node {
    fun getBit(): PacketBit
    fun get__v(): ClientProt
    fun get__n(): Int
    fun get__y(): Int
}
