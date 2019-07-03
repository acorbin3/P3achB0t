package com.p3achb0t._runelite_interfaces

interface MidiFileReader {
    fun getBuffer(): Buffer
    fun getDivision(): Int
    fun getTrackLengths(): IntArray
    fun getTrackPositions(): IntArray
    fun getTrackStarts(): IntArray
    fun get__g(): Int
    fun get__u(): IntArray
    fun get__e(): Long
}
