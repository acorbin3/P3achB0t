package com.p3achb0t.api.interfaces

interface MusicTrack : Node {
    fun getMidi(): ByteArray
    fun getTable(): NodeHashTable
}
