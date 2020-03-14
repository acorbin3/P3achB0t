package com.p3achb0t.api.interfaces

interface PacketWriter {
    fun getBit(): PacketBit
    fun getBitNodes(): IterableNodeDeque
    fun getIsaac(): Isaac
    fun getPacket(): Packet
    fun getServerPacket0(): ServerProt
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__j(): Boolean
    fun get__h(): ServerProt
    fun get__n(): ServerProt
    fun get__v(): ServerProt
    fun get__m(): Int
    fun get__o(): Int
    fun get__p(): Int
}
