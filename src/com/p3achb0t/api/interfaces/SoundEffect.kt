package com.p3achb0t.api.interfaces

interface SoundEffect {
    fun getEnd(): Int
    fun getInstruments(): Array<Instrument>
    fun getStart(): Int
}
