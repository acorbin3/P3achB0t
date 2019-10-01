package com.p3achb0t._runestar_interfaces

interface PacketWriter {
	fun getBit(): PacketBit
	fun getBitNodes(): IterableNodeDeque
	fun getIsaac(): Isaac
	fun getPacket(): Packet
	fun getServerPacket0(): ServerProt
	fun getServerPacket0Length(): Int
	fun getSocket0(): AbstractSocket
    fun get__x(): Boolean
    fun get__c(): ServerProt
	fun get__r(): ServerProt
    fun get__y(): ServerProt
    fun get__d(): Int
	fun get__f(): Int
    fun get__i(): Int
}
