package com.p3achb0t._runestar_interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__u(): Boolean
    fun get__aa(): Array<PcmStream>
    fun get__ay(): Array<PcmStream>
    fun get__au(): Int
    fun get__b(): Int
    fun get__r(): Int
    fun get__w(): Int
    fun get__y(): Int
    fun get__a(): Long
}
