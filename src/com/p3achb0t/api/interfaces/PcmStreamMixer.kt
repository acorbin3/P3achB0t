package com.p3achb0t.api.interfaces

interface PcmStreamMixer : PcmStream {
    fun getSubStreams(): NodeDeque
    fun get__t(): Int
    fun get__x(): Int
    fun get__v(): NodeDeque
}
