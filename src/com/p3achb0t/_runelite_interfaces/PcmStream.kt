package com.p3achb0t._runelite_interfaces

interface PcmStream : Node {
    fun getActive(): Boolean
    fun getAfter(): PcmStream
    fun getSound(): AbstractSound
    fun get__s(): Int
}
