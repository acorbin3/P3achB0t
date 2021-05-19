package com.p3achb0t.api.interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__n(): Boolean
    fun get__al(): Array<PcmStream>
    fun get__av(): Array<PcmStream>
    fun get__a(): Int
    fun get__at(): Int
    fun get__f(): Int
    fun get__m(): Int
    fun get__s(): Int
    fun get__w(): Long
}
