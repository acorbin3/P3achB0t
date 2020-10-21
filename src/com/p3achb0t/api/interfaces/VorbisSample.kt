package com.p3achb0t.api.interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__d(): Boolean
    fun get__q(): Boolean
    fun get__j(): Array<Float>
    fun get__ah(): Int
    fun get__ay(): Int
    fun get__v(): Int
    fun get__w(): Int
}
