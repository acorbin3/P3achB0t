package com.p3achb0t._runelite_interfaces

interface MusicTrack : Node {
    fun getMidi(): Array<Byte>
    fun getTable(): NodeHashTable
}
