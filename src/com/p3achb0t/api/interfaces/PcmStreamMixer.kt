package com.p3achb0t.api.interfaces

interface PcmStreamMixer : PcmStream {
    fun getSubStreams(): NodeDeque
    fun get__p(): Int
    fun get__u(): Int
    fun get__o(): NodeDeque
}
