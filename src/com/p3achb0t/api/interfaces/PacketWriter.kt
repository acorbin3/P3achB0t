package com.p3achb0t.api.interfaces

interface PacketWriter {
    fun getBit(): PacketBit
    fun getBitNodes(): IterableNodeDeque
    fun getIsaac(): Isaac
    fun getPacket(): Packet
    fun getServerPacket0(): ServerProt
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__q(): Boolean
    fun get__a(): ServerProt
    fun get__b(): ServerProt
    fun get__w(): ServerProt
    fun get__d(): Int
    fun get__l(): Int
    fun get__s(): Int
}
