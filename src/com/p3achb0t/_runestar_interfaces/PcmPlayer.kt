package com.p3achb0t._runestar_interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): IntArray
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__f(): Boolean
    fun get__af(): Array<PcmStream>
    fun get__at(): Array<PcmStream>
    fun get__ar(): Int
    fun get__c(): Int
    fun get__g(): Int
    fun get__l(): Int
    fun get__o(): Int
    fun get__y(): Long
}
