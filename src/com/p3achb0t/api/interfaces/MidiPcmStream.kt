package com.p3achb0t.api.interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__ao(): Boolean
    fun get__ab(): Array<Array<MusicPatchNode>>
    fun get__e(): Array<Array<MusicPatchNode>>
    fun get__k(): Int
    fun get__s(): Int
    fun get__b(): IntArray
    fun get__d(): IntArray
    fun get__g(): IntArray
    fun get__h(): IntArray
    fun get__i(): IntArray
    fun get__j(): IntArray
    fun get__l(): IntArray
    fun get__m(): IntArray
    fun get__n(): IntArray
    fun get__o(): IntArray
    fun get__p(): IntArray
    fun get__r(): IntArray
    fun get__t(): IntArray
    fun get__u(): IntArray
    fun get__x(): IntArray
    fun get__am(): Long
    fun get__ap(): Long
}
