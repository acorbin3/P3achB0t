package com.p3achb0t.api.interfaces

interface PcmStreamMixer : PcmStream {
    fun getSubStreams(): NodeDeque
    fun get__e(): Int
    fun get__o(): Int
    fun get__t(): NodeDeque
}
