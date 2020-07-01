package com.p3achb0t.api.interfaces

interface PcmStreamMixer : PcmStream {
    fun getSubStreams(): NodeDeque
    fun get__j(): Int
    fun get__q(): Int
    fun get__o(): NodeDeque
}
