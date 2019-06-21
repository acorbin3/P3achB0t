package com.p3achb0t._runelite_interfaces

interface RawSound : AbstractSound {
    fun getEnd(): Int
    fun getSampleRate(): Int
    fun getSamples(): Array<Byte>
    fun getStart(): Int
    fun get__o(): Boolean
}
