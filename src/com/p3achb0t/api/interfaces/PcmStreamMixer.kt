package com.p3achb0t.api.interfaces

interface PcmStreamMixer : PcmStream {
    fun getSubStreams(): NodeDeque
    fun get__l(): Int
    fun get__m(): Int
    fun get__b(): NodeDeque
}
