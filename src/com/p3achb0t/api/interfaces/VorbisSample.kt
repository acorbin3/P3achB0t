package com.p3achb0t.api.interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__g(): Boolean
    fun get__j(): Boolean
    fun get__y(): Array<Float>
    fun get__ac(): Int
    fun get__ae(): Int
    fun get__q(): Int
    fun get__r(): Int
}
