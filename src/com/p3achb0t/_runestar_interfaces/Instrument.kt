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
    fun get__b(): SoundEnvelope
    fun get__e(): SoundEnvelope
    fun get__f(): SoundEnvelope
    fun get__i(): SoundEnvelope
    fun get__p(): SoundEnvelope
    fun get__s(): SoundEnvelope
    fun get__w(): SoundEnvelope
    fun get__x(): SoundEnvelope
    fun get__y(): SoundEnvelope
}
