package com.p3achb0t.api.interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__e(): Boolean
    fun get__ap(): Array<PcmStream>
    fun get__au(): Array<PcmStream>
    fun get__am(): Int
    fun get__c(): Int
    fun get__j(): Int
    fun get__p(): Int
    fun get__u(): Int
    fun get__l(): Long
}
