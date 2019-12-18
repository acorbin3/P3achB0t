package com.p3achb0t._runestar_interfaces

interface VorbisSample : Node {
    fun getAudioBlocks(): Array<ByteArray>
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__b(): Boolean
    fun get__h(): Boolean
    fun get__n(): Array<Float>
    fun get__ah(): Int
    fun get__aq(): Int
    fun get__u(): Int
    fun get__z(): Int
}
