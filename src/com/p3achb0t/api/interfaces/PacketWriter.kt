package com.p3achb0t.api.interfaces

interface PacketWriter {
    fun getBit(): PacketBit
    fun getBitNodes(): IterableNodeDeque
    fun getIsaac(): Isaac
    fun getPacket(): Packet
    fun getServerPacket0(): ServerProt
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__t(): Boolean
    fun get__n(): ServerProt
    fun get__p(): ServerProt
    fun get__u(): ServerProt
    fun get__e(): Int
    fun get__k(): Int
    fun get__s(): Int
}
