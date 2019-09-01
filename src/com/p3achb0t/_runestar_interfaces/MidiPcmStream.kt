package com.p3achb0t._runestar_interfaces

interface MidiPcmStream: PcmStream{
	fun getMidiFile(): MidiFileReader
	fun getMusicPatches(): NodeHashTable
	fun getPatchStream(): MusicPatchPcmStream
	fun getTrack(): Int
	fun getTrackLength(): Int
	fun get__ai(): Boolean
	fun get__av(): Array<Array<MusicPatchNode>>
	fun get__o(): Array<Array<MusicPatchNode>>
	fun get__i(): Int
	fun get__j(): Int
	fun get__a(): IntArray
	fun get__c(): IntArray
	fun get__d(): IntArray
	fun get__f(): IntArray
	fun get__h(): IntArray
	fun get__k(): IntArray
	fun get__l(): IntArray
	fun get__m(): IntArray
	fun get__n(): IntArray
	fun get__q(): IntArray
	fun get__t(): IntArray
	fun get__u(): IntArray
	fun get__v(): IntArray
	fun get__w(): IntArray
	fun get__x(): IntArray
	fun get__aj(): Long
	fun get__an(): Long
}
