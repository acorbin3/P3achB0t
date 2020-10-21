package com.p3achb0t.api.interfaces

interface MidiFileReader {
    fun getDivision(): Int
    fun getPacket(): Packet
    fun getTrackLengths(): IntArray
    fun getTrackPositions(): IntArray
    fun getTrackStarts(): IntArray
    fun get__k(): Int
    fun get__q(): IntArray
    fun get__u(): Long
}
