package com.p3achb0t.api.interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__e(): Boolean
    fun get__i(): Boolean
    fun get__v(): Array<Float>
    fun get__aj(): Int
    fun get__aw(): Int
    fun get__x(): Int
    fun get__z(): Int
}
