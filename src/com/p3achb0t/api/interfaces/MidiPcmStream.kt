package com.p3achb0t.api.interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__am(): Boolean
    fun get__ac(): Array<Array<MusicPatchNode>>
    fun get__y(): Array<Array<MusicPatchNode>>
    fun get__o(): Int
    fun get__u(): Int
    fun get__a(): IntArray
    fun get__b(): IntArray
    fun get__e(): IntArray
    fun get__g(): IntArray
    fun get__h(): IntArray
    fun get__i(): IntArray
    fun get__k(): IntArray
    fun get__l(): IntArray
    fun get__m(): IntArray
    fun get__n(): IntArray
    fun get__p(): IntArray
    fun get__s(): IntArray
    fun get__w(): IntArray
    fun get__x(): IntArray
    fun get__z(): IntArray
    fun get__aj(): Long
    fun get__at(): Long
}
