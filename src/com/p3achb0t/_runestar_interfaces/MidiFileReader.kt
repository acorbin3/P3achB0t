package com.p3achb0t._runestar_interfaces

interface MidiFileReader {
    fun getDivision(): Int
    fun getPacket(): Packet
    fun getTrackLengths(): IntArray
    fun getTrackPositions(): IntArray
    fun getTrackStarts(): IntArray
    fun get__q(): Int
    fun get__v(): IntArray
    fun get__t(): Long
}
