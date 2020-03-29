package com.p3achb0t.api.interfaces

interface BufferedNetSocket : AbstractSocket {
    fun getSink(): BufferedSink
    fun getSocket(): Any
    fun getSource(): BufferedSource
}
