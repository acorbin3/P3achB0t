package com.p3achb0t.api.interfaces

interface PacketWriter {
    fun getBit(): PacketBit
    fun getBitNodes(): IterableNodeDeque
    fun getIsaac(): Isaac
    fun getPacket(): Packet
    fun getServerPacket0(): ServerProt
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__a(): Boolean
    fun get__c(): ServerProt
    fun get__h(): ServerProt
    fun get__y(): ServerProt
    fun get__q(): Int
    fun get__w(): Int
    fun get__z(): Int
}
