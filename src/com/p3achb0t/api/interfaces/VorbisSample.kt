package com.p3achb0t.api.interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__o(): Boolean
    fun get__u(): Boolean
    fun get__v(): Array<Float>
    fun get__ap(): Int
    fun get__au(): Int
    fun get__j(): Int
    fun get__r(): Int
}
