package com.p3achb0t._runestar_interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__am(): Boolean
    fun get__af(): Array<Array<MusicPatchNode>>
    fun get__j(): Array<Array<MusicPatchNode>>
    fun get__b(): Int
    fun get__f(): Int
    fun get__g(): IntArray
    fun get__h(): IntArray
    fun get__i(): IntArray
    fun get__k(): IntArray
    fun get__l(): IntArray
    fun get__m(): IntArray
    fun get__o(): IntArray
    fun get__p(): IntArray
    fun get__q(): IntArray
    fun get__s(): IntArray
    fun get__t(): IntArray
    fun get__v(): IntArray
    fun get__x(): IntArray
    fun get__y(): IntArray
    fun get__z(): IntArray
    fun get__ao(): Long
    fun get__aw(): Long
}
