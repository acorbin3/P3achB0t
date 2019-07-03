package com.p3achb0t._runelite_interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__g(): ByteArray
    fun get__o(): ByteArray
    fun get__w(): ByteArray
    fun get__u(): Array<MusicPatchNode2>
    fun get__m(): Int
    fun get__l(): IntArray
    fun get__q(): ShortArray
}
