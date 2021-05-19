package com.p3achb0t.api.interfaces

interface PacketWriter {
    fun getBit(): PacketBit
    fun getBitNodes(): IterableNodeDeque
    fun getIsaac(): Isaac
    fun getPacket(): Packet
    fun getServerPacket0(): ServerProt
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__b(): Boolean
    fun get__k(): ServerProt
    fun get__r(): ServerProt
    fun get__x(): ServerProt
    fun get__i(): Int
    fun get__o(): Int
    fun get__q(): Int
}
