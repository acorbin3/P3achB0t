package com.p3achb0t._runestar_interfaces

interface MusicPatch: Node{
	fun getRawSounds(): Array<RawSound>
	fun get__b(): ByteArray
	fun get__k(): ByteArray
	fun get__p(): ByteArray
	fun get__l(): Array<MusicPatchNode2>
	fun get__q(): Int
	fun get__i(): IntArray
	fun get__e(): ShortArray
}
