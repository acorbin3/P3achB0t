package com.p3achb0t.api.interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__i(): Boolean
    fun get__ah(): Array<PcmStream>
    fun get__ay(): Array<PcmStream>
    fun get__a(): Int
    fun get__ar(): Int
    fun get__d(): Int
    fun get__r(): Int
    fun get__w(): Int
    fun get__h(): Long
}
