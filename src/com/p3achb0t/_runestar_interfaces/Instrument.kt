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
    fun get__d(): SoundEnvelope
    fun get__h(): SoundEnvelope
    fun get__k(): SoundEnvelope
    fun get__m(): SoundEnvelope
    fun get__q(): SoundEnvelope
    fun get__v(): SoundEnvelope
    fun get__w(): SoundEnvelope
    fun get__x(): SoundEnvelope
    fun get__z(): SoundEnvelope
}
