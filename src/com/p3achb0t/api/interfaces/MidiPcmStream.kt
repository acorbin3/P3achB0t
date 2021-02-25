package com.p3achb0t.api.interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__ag(): Boolean
    fun get__ay(): Array<Array<MusicPatchNode>>
    fun get__j(): Array<Array<MusicPatchNode>>
    fun get__d(): Int
    fun get__v(): Int
    fun get__b(): IntArray
    fun get__c(): IntArray
    fun get__e(): IntArray
    fun get__h(): IntArray
    fun get__l(): IntArray
    fun get__m(): IntArray
    fun get__o(): IntArray
    fun get__p(): IntArray
    fun get__q(): IntArray
    fun get__r(): IntArray
    fun get__s(): IntArray
    fun get__t(): IntArray
    fun get__u(): IntArray
    fun get__y(): IntArray
    fun get__z(): IntArray
    fun get__aq(): Long
    fun get__at(): Long
}
