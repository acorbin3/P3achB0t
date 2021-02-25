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
    fun get__d(): SoundEnvelope
    fun get__e(): SoundEnvelope
    fun get__h(): SoundEnvelope
    fun get__k(): SoundEnvelope
    fun get__n(): SoundEnvelope
    fun get__v(): SoundEnvelope
    fun get__y(): SoundEnvelope
    fun get__z(): SoundEnvelope
}
