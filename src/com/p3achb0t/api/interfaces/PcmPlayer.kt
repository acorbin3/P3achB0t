package com.p3achb0t.api.interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__ag(): Array<PcmStream>
    fun get__ar(): Array<PcmStream>
    fun get__i(): Boolean
    fun get__ai(): Int
    fun get__g(): Int
    fun get__t(): Int
    fun get__w(): Int
    fun get__x(): Int
    fun get__q(): Long
}
