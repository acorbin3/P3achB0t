package com.p3achb0t._runestar_interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__e(): ByteArray
    fun get__p(): ByteArray
    fun get__w(): ByteArray
    fun get__b(): Array<MusicPatchNode2>
    fun get__f(): Int
    fun get__x(): IntArray
    fun get__y(): ShortArray
}
