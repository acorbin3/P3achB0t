package com.p3achb0t._runestar_interfaces

interface PcmPlayer {
	fun getCapacity(): Int
	fun getFrequency(): Int
	fun getNextPosition(): Int
	fun getRetryTimeMs(): Long
	fun getSamples(): IntArray
	fun getStream0(): PcmStream
	fun getTimeMs(): Long
    fun get__o(): Boolean
    fun get__an(): Array<PcmStream>
    fun get__at(): Array<PcmStream>
    fun get__a(): Int
    fun get__aj(): Int
    fun get__b(): Int
    fun get__h(): Int
    fun get__l(): Int
    fun get__m(): Long
}
