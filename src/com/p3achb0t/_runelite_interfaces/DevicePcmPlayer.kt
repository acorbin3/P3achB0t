package com.p3achb0t._runelite_interfaces

interface DevicePcmPlayer : PcmPlayer {
    fun getByteSamples(): Array<Byte>
    fun getCapacity2(): Int
    fun getFormat(): Any
    fun getLine(): Any
}
