package com.p3achb0t._runelite_interfaces

interface MidiFileReader {
    fun getBuffer(): Buffer
    fun getDivision(): Int
    fun getTrackLengths(): Array<Int>
    fun getTrackPositions(): Array<Int>
    fun getTrackStarts(): Array<Int>
    fun get__g(): Int
    fun get__u(): Array<Int>
    fun get__e(): Long
}
