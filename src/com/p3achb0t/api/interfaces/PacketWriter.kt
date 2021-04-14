package com.p3achb0t.api.interfaces

interface PacketWriter {
    fun getBit(): PacketBit
    fun getBitNodes(): IterableNodeDeque
    fun getIsaac(): Isaac
    fun getPacket(): Packet
    fun getServerPacket0(): ServerProt
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__h(): Boolean
    fun get__c(): ServerProt
    fun get__d(): ServerProt
    fun get__m(): ServerProt
    fun get__l(): Int
    fun get__n(): Int
    fun get__u(): Int
}
