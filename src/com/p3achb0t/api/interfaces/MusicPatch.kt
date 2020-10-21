package com.p3achb0t.api.interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__k(): ByteArray
    fun get__m(): ByteArray
    fun get__z(): ByteArray
    fun get__q(): Array<MusicPatchNode2>
    fun get__f(): Int
    fun get__c(): IntArray
    fun get__l(): ShortArray
}
