package com.p3achb0t.api.interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__ao(): Boolean
    fun get__c(): Int
    fun get__o(): Int
    fun get__b(): IntArray
    fun get__f(): IntArray
    fun get__g(): IntArray
    fun get__i(): IntArray
    fun get__j(): IntArray
    fun get__l(): IntArray
    fun get__m(): IntArray
    fun get__q(): IntArray
    fun get__s(): IntArray
    fun get__t(): IntArray
    fun get__v(): IntArray
    fun get__w(): IntArray
    fun get__x(): IntArray
    fun get__y(): IntArray
    fun get__z(): IntArray
    fun get__ag(): Array<Array<MusicPatchNode>>
    fun get__n(): Array<Array<MusicPatchNode>>
    fun get__at(): Long
    fun get__av(): Long
}
