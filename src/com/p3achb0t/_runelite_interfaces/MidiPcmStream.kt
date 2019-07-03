package com.p3achb0t._runelite_interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__aj(): Boolean
    fun get__ag(): Array<Array<MusicPatchNode>>
    fun get__v(): Array<Array<MusicPatchNode>>
    fun get__f(): Int
    fun get__q(): Int
    fun get__b(): IntArray
    fun get__c(): IntArray
    fun get__d(): IntArray
    fun get__e(): IntArray
    fun get__g(): IntArray
    fun get__h(): IntArray
    fun get__k(): IntArray
    fun get__l(): IntArray
    fun get__n(): IntArray
    fun get__o(): IntArray
    fun get__p(): IntArray
    fun get__r(): IntArray
    fun get__u(): IntArray
    fun get__x(): IntArray
    fun get__y(): IntArray
    fun get__ac(): Long
    fun get__ay(): Long
}
