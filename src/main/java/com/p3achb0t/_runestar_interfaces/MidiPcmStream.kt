package com.p3achb0t._runestar_interfaces

interface MidiPcmStream: PcmStream{
	fun getMidiFile(): MidiFileReader
	fun getMusicPatches(): NodeHashTable
	fun getPatchStream(): MusicPatchPcmStream
	fun getTrack(): Int
	fun getTrackLength(): Int
	fun get__as(): Boolean
	fun get__al(): Array<Array<MusicPatchNode>>
	fun get__z(): Array<Array<MusicPatchNode>>
	fun get__e(): Int
	fun get__p(): Int
	fun get__b(): IntArray
	fun get__c(): IntArray
	fun get__d(): IntArray
	fun get__f(): IntArray
	fun get__h(): IntArray
	fun get__i(): IntArray
	fun get__k(): IntArray
	fun get__l(): IntArray
	fun get__m(): IntArray
	fun get__n(): IntArray
	fun get__o(): IntArray
	fun get__s(): IntArray
	fun get__t(): IntArray
	fun get__u(): IntArray
	fun get__x(): IntArray
	fun get__ag(): Long
	fun get__ar(): Long
}
