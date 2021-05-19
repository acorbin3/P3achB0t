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
    fun get__c(): SoundEnvelope
    fun get__g(): SoundEnvelope
    fun get__h(): SoundEnvelope
    fun get__l(): SoundEnvelope
    fun get__o(): SoundEnvelope
    fun get__p(): SoundEnvelope
    fun get__t(): SoundEnvelope
    fun get__v(): SoundEnvelope
    fun get__z(): SoundEnvelope
}
