package com.p3achb0t.api.interfaces

interface PcmStreamMixer : PcmStream {
    fun getSubStreams(): NodeDeque
    fun get__s(): Int
    fun get__t(): Int
    fun get__k(): NodeDeque
}
