package com.p3achb0t._runestar_interfaces

interface PcmPlayer {
	fun getCapacity(): Int
	fun getFrequency(): Int
	fun getNextPosition(): Int
	fun getRetryTimeMs(): Long
	fun getSamples(): IntArray
	fun getStream0(): PcmStream
	fun getTimeMs(): Long
	fun get__z(): Boolean
	fun get__ar(): Array<PcmStream>
	fun get__ax(): Array<PcmStream>
	fun get__ag(): Int
	fun get__n(): Int
	fun get__o(): Int
	fun get__t(): Int
	fun get__y(): Int
	fun get__s(): Long
}
