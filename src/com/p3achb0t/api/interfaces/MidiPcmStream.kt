package com.p3achb0t.api.interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__ai(): Boolean
    fun get__ae(): Array<Array<MusicPatchNode>>
    fun get__f(): Array<Array<MusicPatchNode>>
    fun get__k(): Int
    fun get__m(): Int
    fun get__c(): IntArray
    fun get__d(): IntArray
    fun get__e(): IntArray
    fun get__j(): IntArray
    fun get__l(): IntArray
    fun get__n(): IntArray
    fun get__o(): IntArray
    fun get__p(): IntArray
    fun get__q(): IntArray
    fun get__r(): IntArray
    fun get__s(): IntArray
    fun get__t(): IntArray
    fun get__v(): IntArray
    fun get__y(): IntArray
    fun get__z(): IntArray
    fun get__af(): Long
    fun get__ar(): Long
}
