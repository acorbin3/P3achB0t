package com.p3achb0t._runelite_interfaces

interface BufferedSource {
    fun getBuffer(): ByteArray
    fun getCapacity(): Int
    fun getException(): Any
    fun getInputStream(): Any
    fun getLimit(): Int
    fun getPosition(): Int
    fun getThread(): Any
}
