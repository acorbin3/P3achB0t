package com.p3achb0t.api.interfaces

interface PacketWriter {
    fun getBit(): PacketBit
    fun getBitNodes(): IterableNodeDeque
    fun getIsaac(): Isaac
    fun getPacket(): Packet
    fun getServerPacket0(): ServerProt
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__g(): Boolean
    fun get__a(): ServerProt
    fun get__d(): ServerProt
    fun get__h(): ServerProt
    fun get__m(): Int
    fun get__n(): Int
    fun get__s(): Int
}
