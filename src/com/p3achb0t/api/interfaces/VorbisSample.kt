package com.p3achb0t.api.interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__j(): Boolean
    fun get__w(): Boolean
    fun get__m(): Array<Float>
    fun get__ag(): Int
    fun get__ar(): Int
    fun get__x(): Int
    fun get__z(): Int
}
