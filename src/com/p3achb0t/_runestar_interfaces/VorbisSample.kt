package com.p3achb0t._runestar_interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__m(): Boolean
    fun get__p(): Boolean
    fun get__d(): Array<Float>
    fun get__ak(): Int
    fun get__aw(): Int
    fun get__s(): Int
    fun get__t(): Int
}
