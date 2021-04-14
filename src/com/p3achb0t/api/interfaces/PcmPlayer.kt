package com.p3achb0t.api.interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__y(): Boolean
    fun get__aj(): Array<PcmStream>
    fun get__aw(): Array<PcmStream>
    fun get__a(): Int
    fun get__at(): Int
    fun get__i(): Int
    fun get__r(): Int
    fun get__x(): Int
    fun get__s(): Long
}
