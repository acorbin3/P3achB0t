package com.p3achb0t._runestar_interfaces

interface MusicPatch: Node{
	fun getRawSounds(): Array<RawSound>
	fun get__k(): ByteArray
    fun get__t(): ByteArray
    fun get__u(): ByteArray
    fun get__n(): Array<MusicPatchNode2>
    fun get__s(): Int
    fun get__q(): IntArray
    fun get__i(): ShortArray
}
