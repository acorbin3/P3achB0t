package com.p3achb0t._runestar_interfaces

interface SoundEnvelope {
    fun getAmplitude(): Int
    fun getDurations(): IntArray
    fun getEnd(): Int
    fun getForm(): Int
    fun getMax(): Int
    fun getPhaseIndex(): Int
    fun getPhases(): IntArray
    fun getSegments(): Int
    fun getStart(): Int
    fun getStep(): Int
    fun getTicks(): Int
}
