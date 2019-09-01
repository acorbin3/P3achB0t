package com.p3achb0t._runestar_interfaces

interface VorbisSample: Node{
	fun getAudioBlocks(): Array<ByteArray>
	fun getEnd(): Int
	fun getSampleCount(): Int
	fun getSampleRate(): Int
	fun getSamples(): ByteArray
	fun getStart(): Int
	fun get__l(): Boolean
	fun get__n(): Boolean
	fun get__z(): Array<Float>
	fun get__a(): Int
	fun get__an(): Int
	fun get__at(): Int
	fun get__w(): Int
}
