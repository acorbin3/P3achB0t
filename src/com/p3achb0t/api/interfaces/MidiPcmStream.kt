package com.p3achb0t.api.interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__ax(): Boolean
    fun get__an(): Array<Array<MusicPatchNode>>
    fun get__d(): Array<Array<MusicPatchNode>>
    fun get__v(): Int
    fun get__x(): Int
    fun get__e(): IntArray
    fun get__g(): IntArray
    fun get__j(): IntArray
    fun get__k(): IntArray
    fun get__l(): IntArray
    fun get__m(): IntArray
    fun get__n(): IntArray
    fun get__o(): IntArray
    fun get__p(): IntArray
    fun get__q(): IntArray
    fun get__r(): IntArray
    fun get__s(): IntArray
    fun get__t(): IntArray
    fun get__u(): IntArray
    fun get__z(): IntArray
    fun get__ae(): Long
    fun get__as(): Long
}
