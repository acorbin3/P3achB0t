package com.p3achb0t.api.interfaces

interface PcmStreamMixer : PcmStream {
    fun getSubStreams(): NodeDeque
    fun get__f(): Int
    fun get__y(): Int
    fun get__n(): NodeDeque
}
