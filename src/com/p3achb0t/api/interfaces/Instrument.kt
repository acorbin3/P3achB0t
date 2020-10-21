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
    fun get__c(): SoundEnvelope
    fun get__f(): SoundEnvelope
    fun get__k(): SoundEnvelope
    fun get__l(): SoundEnvelope
    fun get__m(): SoundEnvelope
    fun get__p(): SoundEnvelope
    fun get__q(): SoundEnvelope
    fun get__z(): SoundEnvelope
}
