package com.p3achb0t.api.interfaces

interface PcmStream : Node {
    fun getActive(): Boolean
    fun getAfter(): PcmStream
    fun getSound(): AbstractSound
    fun get__v(): Int
}
