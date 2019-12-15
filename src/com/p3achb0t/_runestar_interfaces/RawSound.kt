package com.p3achb0t._runestar_interfaces

interface RawSound : AbstractSound {
    fun getEnd(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__z(): Boolean
}
