package com.p3achb0t._runestar_interfaces

interface Instrument {
	fun getDelayDecay(): Int
	fun getDelayTime(): Int
	fun getDuration(): Int
	fun getFilter(): AudioFilter
	fun getOffset(): Int
	fun getOscillatorDelays(): IntArray
	fun getOscillatorPitch(): IntArray
	fun getOscillatorVolume(): IntArray
	fun get__i(): SoundEnvelope
    fun get__j(): SoundEnvelope
	fun get__k(): SoundEnvelope
    fun get__n(): SoundEnvelope
	fun get__p(): SoundEnvelope
	fun get__q(): SoundEnvelope
    fun get__s(): SoundEnvelope
    fun get__t(): SoundEnvelope
    fun get__u(): SoundEnvelope
}
