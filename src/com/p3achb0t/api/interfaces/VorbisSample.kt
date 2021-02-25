package com.p3achb0t.api.interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__h(): Boolean
    fun get__p(): Boolean
    fun get__g(): Array<Float>
    fun get__ak(): Int
    fun get__at(): Int
    fun get__t(): Int
    fun get__u(): Int
}
