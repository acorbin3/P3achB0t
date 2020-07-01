package com.p3achb0t.api.interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__ao(): Boolean
    fun get__ah(): Array<Array<MusicPatchNode>>
    fun get__f(): Array<Array<MusicPatchNode>>
    fun get__o(): Int
    fun get__q(): Int
    fun get__a(): IntArray
    fun get__b(): IntArray
    fun get__c(): IntArray
    fun get__e(): IntArray
    fun get__g(): IntArray
    fun get__i(): IntArray
    fun get__j(): IntArray
    fun get__l(): IntArray
    fun get__n(): IntArray
    fun get__p(): IntArray
    fun get__t(): IntArray
    fun get__u(): IntArray
    fun get__x(): IntArray
    fun get__y(): IntArray
    fun get__z(): IntArray
    fun get__ab(): Long
    fun get__ag(): Long
}
