package com.p3achb0t.api.interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__g(): ByteArray
    fun get__l(): ByteArray
    fun get__t(): ByteArray
    fun get__z(): Array<MusicPatchNode2>
    fun get__h(): Int
    fun get__v(): IntArray
    fun get__o(): ShortArray
}
