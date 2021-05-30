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
    fun get__b(): SoundEnvelope
    fun get__e(): SoundEnvelope
    fun get__f(): SoundEnvelope
    fun get__j(): SoundEnvelope
    fun get__n(): SoundEnvelope
    fun get__p(): SoundEnvelope
    fun get__r(): SoundEnvelope
    fun get__v(): SoundEnvelope
    fun get__y(): SoundEnvelope
}
