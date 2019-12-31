package com.p3achb0t._runestar_interfaces

interface MidiPcmStream : PcmStream {
    fun getMidiFile(): MidiFileReader
    fun getMusicPatches(): NodeHashTable
    fun getPatchStream(): MusicPatchPcmStream
    fun getTrack(): Int
    fun getTrackLength(): Int
    fun get__aw(): Boolean
    fun get__aa(): Array<Array<MusicPatchNode>>
    fun get__q(): Array<Array<MusicPatchNode>>
    fun get__i(): Int
    fun get__y(): Int
    fun get__a(): IntArray
    fun get__b(): IntArray
    fun get__c(): IntArray
    fun get__d(): IntArray
    fun get__e(): IntArray
    fun get__g(): IntArray
    fun get__h(): IntArray
    fun get__m(): IntArray
    fun get__o(): IntArray
    fun get__p(): IntArray
    fun get__r(): IntArray
    fun get__u(): IntArray
    fun get__v(): IntArray
    fun get__x(): IntArray
    fun get__z(): IntArray
    fun get__ah(): Long
    fun get__au(): Long
}
