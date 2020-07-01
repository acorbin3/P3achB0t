package com.p3achb0t.api.interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__f(): Boolean
    fun get__ab(): Array<PcmStream>
    fun get__am(): Array<PcmStream>
    fun get__ag(): Int
    fun get__e(): Int
    fun get__r(): Int
    fun get__t(): Int
    fun get__x(): Int
    fun get__i(): Long
}
