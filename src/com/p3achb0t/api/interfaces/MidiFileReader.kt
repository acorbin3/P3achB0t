package com.p3achb0t.api.interfaces

interface MidiFileReader {
    fun getDivision(): Int
    fun getPacket(): Packet
    fun getTrackLengths(): IntArray
    fun getTrackPositions(): IntArray
    fun getTrackStarts(): IntArray
    fun get__n(): Int
    fun get__j(): IntArray
    fun get__l(): Long
}
