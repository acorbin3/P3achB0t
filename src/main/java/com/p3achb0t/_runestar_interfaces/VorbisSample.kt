package com.p3achb0t._runestar_interfaces

interface VorbisSample: Node{
	fun getAudioBlocks(): Array<ByteArray>
	fun getEnd(): Int
	fun getSampleCount(): Int
	fun getSampleRate(): Int
	fun getSamples(): ByteArray
	fun getStart(): Int
	fun get__l(): Boolean
	fun get__o(): Boolean
	fun get__j(): Array<Float>
	fun get__ar(): Int
	fun get__ax(): Int
	fun get__h(): Int
	fun get__t(): Int
}
