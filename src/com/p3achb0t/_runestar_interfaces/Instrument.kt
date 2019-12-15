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
    fun get__f(): SoundEnvelope
    fun get__g(): SoundEnvelope
    fun get__h(): SoundEnvelope
    fun get__n(): SoundEnvelope
    fun get__p(): SoundEnvelope
    fun get__u(): SoundEnvelope
    fun get__y(): SoundEnvelope
    fun get__z(): SoundEnvelope
}
