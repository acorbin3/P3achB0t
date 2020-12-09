package com.p3achb0t.api.interfaces

interface Instrument {
    fun getDelayDecay(): Int
    fun getDelayTime(): Int
    fun getDuration(): Int
    fun getFilter(): AudioFilter
    fun getOffset(): Int
    fun getOscillatorDelays(): IntArray
    fun getOscillatorPitch(): IntArray
    fun getOscillatorVolume(): IntArray
    fun get__h(): SoundEnvelope
    fun get__i(): SoundEnvelope
    fun get__j(): SoundEnvelope
    fun get__n(): SoundEnvelope
    fun get__p(): SoundEnvelope
    fun get__t(): SoundEnvelope
    fun get__v(): SoundEnvelope
    fun get__w(): SoundEnvelope
    fun get__x(): SoundEnvelope
}
