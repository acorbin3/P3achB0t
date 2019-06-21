package com.p3achb0t._runelite_interfaces

interface MusicPatch : Node {
    fun getRawSounds(): Array<RawSound>
    fun get__g(): Array<Byte>
    fun get__o(): Array<Byte>
    fun get__w(): Array<Byte>
    fun get__u(): Array<MusicPatchNode2>
    fun get__m(): Int
    fun get__l(): Array<Int>
    fun get__q(): Array<Short>
}
