package com.p3achb0t._runestar_interfaces

interface PacketWriter {
    fun getBit(): PacketBit
    fun getBitNodes(): IterableNodeDeque
    fun getIsaac(): Isaac
    fun getPacket(): Packet
    fun getServerPacket0(): ServerProt
    fun getServerPacket0Length(): Int
    fun getSocket0(): AbstractSocket
    fun get__a(): Boolean
    fun get__k(): ServerProt
    fun get__o(): ServerProt
    fun get__v(): ServerProt
    fun get__c(): Int
    fun get__d(): Int
    fun get__y(): Int
}
