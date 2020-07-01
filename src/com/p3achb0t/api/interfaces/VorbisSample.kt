package com.p3achb0t.api.interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__g(): Boolean
    fun get__t(): Boolean
    fun get__d(): Array<Float>
    fun get__ab(): Int
    fun get__am(): Int
    fun get__e(): Int
    fun get__l(): Int
}
