package com.p3achb0t._runelite_interfaces

interface Instrument {
    fun getDelayDecay(): Int
    fun getDelayTime(): Int
    fun getDuration(): Int
    fun getFilter(): AudioFilter
    fun getOffset(): Int
    fun getOscillatorDelays(): IntArray
    fun getOscillatorPitch(): IntArray
    fun getOscillatorVolume(): IntArray
    fun get__a(): SoundEnvelope
    fun get__f(): SoundEnvelope
    fun get__g(): SoundEnvelope
    fun get__l(): SoundEnvelope
    fun get__m(): SoundEnvelope
    fun get__o(): SoundEnvelope
    fun get__q(): SoundEnvelope
    fun get__u(): SoundEnvelope
    fun get__w(): SoundEnvelope
}
