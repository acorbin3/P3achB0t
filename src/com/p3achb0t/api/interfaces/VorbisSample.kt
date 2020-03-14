package com.p3achb0t.api.interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__b(): Boolean
    fun get__g(): Boolean
    fun get__z(): Array<Float>
    fun get__aa(): Int
    fun get__ay(): Int
    fun get__f(): Int
    fun get__y(): Int
}
