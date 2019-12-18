package com.p3achb0t._runestar_interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__q(): Boolean
    fun get__ah(): Array<PcmStream>
    fun get__aq(): Array<PcmStream>
    fun get__au(): Int
    fun get__h(): Int
    fun get__l(): Int
    fun get__m(): Int
    fun get__u(): Int
    fun get__r(): Long
}
