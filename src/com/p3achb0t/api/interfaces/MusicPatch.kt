package com.p3achb0t.api.interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__j(): ByteArray
    fun get__n(): ByteArray
    fun get__p(): ByteArray
    fun get__g(): Array<MusicPatchNode2>
    fun get__m(): Int
    fun get__u(): IntArray
    fun get__q(): ShortArray
}
