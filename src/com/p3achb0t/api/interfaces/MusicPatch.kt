package com.p3achb0t.api.interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__b(): ByteArray
    fun get__k(): ByteArray
    fun get__p(): ByteArray
    fun get__e(): Array<MusicPatchNode2>
    fun get__f(): Int
    fun get__g(): IntArray
    fun get__u(): ShortArray
}
