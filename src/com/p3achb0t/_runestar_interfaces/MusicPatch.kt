package com.p3achb0t._runestar_interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__d(): ByteArray
    fun get__e(): ByteArray
    fun get__i(): ByteArray
    fun get__g(): Array<MusicPatchNode2>
    fun get__c(): Int
    fun get__l(): IntArray
    fun get__o(): ShortArray
}
