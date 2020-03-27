package com.p3achb0t._runestar_interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__o(): Boolean
    fun get__v(): Boolean
    fun get__b(): Array<Float>
    fun get__af(): Int
    fun get__at(): Int
    fun get__l(): Int
    fun get__r(): Int
}
