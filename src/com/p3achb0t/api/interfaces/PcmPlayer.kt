package com.p3achb0t.api.interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__d(): Boolean
    fun get__ac(): Array<PcmStream>
    fun get__ae(): Array<PcmStream>
    fun get__as(): Int
    fun get__f(): Int
    fun get__g(): Int
    fun get__r(): Int
    fun get__s(): Int
    fun get__k(): Long
}
