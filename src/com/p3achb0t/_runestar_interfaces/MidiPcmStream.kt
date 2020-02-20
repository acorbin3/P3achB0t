package com.p3achb0t._runestar_interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__az(): Boolean
    fun get__ae(): Array<Array<MusicPatchNode>>
    fun get__u(): Array<Array<MusicPatchNode>>
    fun get__o(): Int
    fun get__t(): Int
    fun get__a(): IntArray
    fun get__b(): IntArray
    fun get__d(): IntArray
    fun get__e(): IntArray
    fun get__f(): IntArray
    fun get__g(): IntArray
    fun get__h(): IntArray
    fun get__i(): IntArray
    fun get__j(): IntArray
    fun get__l(): IntArray
    fun get__m(): IntArray
    fun get__p(): IntArray
    fun get__r(): IntArray
    fun get__s(): IntArray
    fun get__y(): IntArray
    fun get__au(): Long
    fun get__ay(): Long
}
