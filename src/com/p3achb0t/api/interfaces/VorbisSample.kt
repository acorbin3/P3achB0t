package com.p3achb0t.api.interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__f(): Boolean
    fun get__z(): Boolean
    fun get__u(): Array<Float>
    fun get__al(): Int
    fun get__av(): Int
    fun get__j(): Int
    fun get__m(): Int
}
