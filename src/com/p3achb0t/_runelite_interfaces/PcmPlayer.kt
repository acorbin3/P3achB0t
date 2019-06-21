package com.p3achb0t._runelite_interfaces

interface PcmPlayer {
    fun getCapacity(): Int
    fun getFrequency(): Int
    fun getNextPosition(): Int
    fun getRetryTimeMs(): Long
    fun getSamples(): Array<Int>
    fun getStream0(): PcmStream
    fun getTimeMs(): Long
    fun get__v(): Boolean
    fun get__ah(): Array<PcmStream>
    fun get__ay(): Array<PcmStream>
    fun get__ac(): Int
    fun get__b(): Int
    fun get__c(): Int
    fun get__y(): Int
    fun get__z(): Int
    fun get__p(): Long
}
