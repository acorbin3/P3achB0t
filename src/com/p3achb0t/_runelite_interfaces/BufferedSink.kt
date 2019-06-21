package com.p3achb0t._runelite_interfaces

interface BufferedSink {
    fun getBuffer(): Array<Byte>
    fun getCapacity(): Int
    fun getException(): Any
    fun getIsClosed0(): Boolean
    fun getLimit(): Int
    fun getOutputStream(): Any
    fun getPosition(): Int
    fun getThread(): Any
}
