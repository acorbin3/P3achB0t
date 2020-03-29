package com.p3achb0t.api.interfaces

interface PcmStreamMixer : PcmStream {
    fun getSubStreams(): NodeDeque
    fun get__d(): Int
    fun get__k(): Int
    fun get__m(): NodeDeque
}
