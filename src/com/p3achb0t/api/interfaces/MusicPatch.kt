package com.p3achb0t.api.interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__p(): ByteArray
    fun get__r(): ByteArray
    fun get__y(): ByteArray
    fun get__j(): Array<MusicPatchNode2>
    fun get__v(): Int
    fun get__b(): IntArray
    fun get__f(): ShortArray
}
