package com.p3achb0t.api.interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__ab(): Boolean
    fun get__f(): Int
    fun get__n(): Int
    fun get__b(): IntArray
    fun get__d(): IntArray
    fun get__h(): IntArray
    fun get__j(): IntArray
    fun get__l(): IntArray
    fun get__p(): IntArray
    fun get__q(): IntArray
    fun get__r(): IntArray
    fun get__s(): IntArray
    fun get__t(): IntArray
    fun get__u(): IntArray
    fun get__w(): IntArray
    fun get__x(): IntArray
    fun get__y(): IntArray
    fun get__z(): IntArray
    fun get__ae(): Array<Array<MusicPatchNode>>
    fun get__i(): Array<Array<MusicPatchNode>>
    fun get__ai(): Long
    fun get__ar(): Long
}
