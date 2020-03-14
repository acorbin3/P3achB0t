package com.p3achb0t.api.interfaces

interface RawSound : AbstractSound {
    fun getEnd(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__i(): Boolean
}
