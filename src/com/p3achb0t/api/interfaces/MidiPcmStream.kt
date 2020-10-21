package com.p3achb0t.api.interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__ao(): Boolean
    fun get__ab(): Array<Array<MusicPatchNode>>
    fun get__i(): Array<Array<MusicPatchNode>>
    fun get__b(): Int
    fun get__l(): Int
    fun get__a(): IntArray
    fun get__c(): IntArray
    fun get__d(): IntArray
    fun get__e(): IntArray
    fun get__g(): IntArray
    fun get__h(): IntArray
    fun get__k(): IntArray
    fun get__m(): IntArray
    fun get__o(): IntArray
    fun get__q(): IntArray
    fun get__t(): IntArray
    fun get__u(): IntArray
    fun get__v(): IntArray
    fun get__w(): IntArray
    fun get__z(): IntArray
    fun get__ar(): Long
    fun get__ay(): Long
}
