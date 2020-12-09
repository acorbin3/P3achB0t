package com.p3achb0t.api.interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__n(): ByteArray
    fun get__t(): ByteArray
    fun get__w(): ByteArray
    fun get__j(): Array<MusicPatchNode2>
    fun get__h(): Int
    fun get__p(): IntArray
    fun get__x(): ShortArray
}
