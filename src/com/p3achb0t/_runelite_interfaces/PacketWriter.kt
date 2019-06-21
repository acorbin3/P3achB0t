package com.p3achb0t._runelite_interfaces

interface PacketWriter {
    fun getBuffer(): Buffer
    fun getIsaacCipher(): IsaacCipher
    fun getPacketBuffer(): PacketBuffer
    fun getPacketBufferNodes(): IterableNodeDeque
    fun getServerPacket0(): ServerPacket
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__e(): Boolean
    fun get__i(): ServerPacket
    fun get__k(): ServerPacket
    fun get__n(): ServerPacket
    fun get__d(): Int
    fun get__q(): Int
    fun get__x(): Int
}
