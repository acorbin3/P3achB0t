package com.p3achb0t.api.interfaces

interface PacketWriter {
    fun getBit(): PacketBit
    fun getBitNodes(): IterableNodeDeque
    fun getIsaac(): Isaac
    fun getPacket(): Packet
    fun getServerPacket0(): ServerProt
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__d(): Boolean
    fun get__c(): ServerProt
    fun get__l(): ServerProt
    fun get__o(): ServerProt
    fun get__f(): Int
    fun get__s(): Int
    fun get__u(): Int
}
