package com.p3achb0t.api.interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__c(): ByteArray
    fun get__y(): ByteArray
    fun get__z(): ByteArray
    fun get__h(): Array<MusicPatchNode2>
    fun get__n(): Int
    fun get__e(): IntArray
    fun get__d(): ShortArray
}
