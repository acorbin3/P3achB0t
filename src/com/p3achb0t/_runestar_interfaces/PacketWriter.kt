package com.p3achb0t._runestar_interfaces

interface PacketWriter {
    fun getBit(): PacketBit
    fun getBitNodes(): IterableNodeDeque
    fun getIsaac(): Isaac
    fun getPacket(): Packet
    fun getServerPacket0(): ServerProt
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__w(): Boolean
    fun get__e(): ServerProt
    fun get__o(): ServerProt
    fun get__x(): ServerProt
    fun get__b(): Int
    fun get__i(): Int
    fun get__k(): Int
}
