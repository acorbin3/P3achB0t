package com.p3achb0t.api.interfaces

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
    fun get__at(): Array<PcmStream>
    fun get__aq(): Int
    fun get__i(): Int
    fun get__m(): Int
    fun get__p(): Int
    fun get__u(): Int
    fun get__o(): Long
}
