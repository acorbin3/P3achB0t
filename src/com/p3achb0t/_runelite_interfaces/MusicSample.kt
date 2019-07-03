package com.p3achb0t._runelite_interfaces

interface MusicSample : Node {
    fun getEnd(): Int
    fun getSampleCount(): Int
    fun getSampleRate(): Int
    fun getSamples(): ByteArray
    fun getStart(): Int
    fun get__b(): Boolean
    fun get__u(): Boolean
    fun get__m(): Array<ByteArray>
    fun get__t(): Array<Float>
    fun get__ah(): Int
    fun get__ay(): Int
    fun get__h(): Int
    fun get__y(): Int
}
