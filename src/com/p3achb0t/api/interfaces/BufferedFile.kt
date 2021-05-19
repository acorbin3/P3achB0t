package com.p3achb0t.api.interfaces

interface BufferedFile {
    fun getAccessFile(): AccessFile
    fun getCapacity(): Long
    fun getReadBuffer(): ByteArray
    fun getWriteBuffer(): ByteArray
    fun get__l(): Int
    fun get__v(): Int
    fun get__b(): Long
    fun get__g(): Long
    fun get__q(): Long
    fun get__t(): Long
    fun get__x(): Long
}
