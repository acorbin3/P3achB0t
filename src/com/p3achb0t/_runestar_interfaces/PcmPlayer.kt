package com.p3achb0t._runestar_interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__j(): Boolean
    fun get__ak(): Array<PcmStream>
    fun get__aw(): Array<PcmStream>
    fun get__ao(): Int
    fun get__m(): Int
    fun get__r(): Int
    fun get__s(): Int
    fun get__v(): Int
    fun get__l(): Long
}
