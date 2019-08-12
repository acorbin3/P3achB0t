package com.p3achb0t._runestar_interfaces

interface MusicTrack: Node{
	fun getMidi(): ByteArray
	fun getTable(): NodeHashTable
}
