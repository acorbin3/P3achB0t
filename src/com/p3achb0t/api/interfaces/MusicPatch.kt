package com.p3achb0t.api.interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__i(): ByteArray
    fun get__t(): ByteArray
    fun get__x(): ByteArray
    fun get__o(): Array<MusicPatchNode2>
    fun get__z(): Int
    fun get__w(): IntArray
    fun get__s(): ShortArray
}
