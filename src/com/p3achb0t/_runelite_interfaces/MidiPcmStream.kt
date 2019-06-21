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
    fun get__b(): Array<Int>
    fun get__c(): Array<Int>
    fun get__d(): Array<Int>
    fun get__e(): Array<Int>
    fun get__g(): Array<Int>
    fun get__h(): Array<Int>
    fun get__k(): Array<Int>
    fun get__l(): Array<Int>
    fun get__n(): Array<Int>
    fun get__o(): Array<Int>
    fun get__p(): Array<Int>
    fun get__r(): Array<Int>
    fun get__u(): Array<Int>
    fun get__x(): Array<Int>
    fun get__y(): Array<Int>
    fun get__ac(): Long
    fun get__ay(): Long
}
