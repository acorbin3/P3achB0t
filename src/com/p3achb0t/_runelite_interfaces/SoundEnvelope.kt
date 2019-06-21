package com.p3achb0t._runelite_interfaces

interface SoundEnvelope {
    fun getAmplitude(): Int
    fun getDurations(): Array<Int>
    fun getEnd(): Int
    fun getForm(): Int
    fun getMax(): Int
    fun getPhaseIndex(): Int
    fun getPhases(): Array<Int>
    fun getSegments(): Int
    fun getStart(): Int
    fun getStep(): Int
    fun getTicks(): Int
}
