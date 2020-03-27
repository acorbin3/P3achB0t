package com.p3achb0t._runestar_interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__d(): ByteArray
    fun get__q(): ByteArray
    fun get__w(): ByteArray
    fun get__v(): Array<MusicPatchNode2>
    fun get__x(): Int
    fun get__z(): IntArray
    fun get__k(): ShortArray
}
