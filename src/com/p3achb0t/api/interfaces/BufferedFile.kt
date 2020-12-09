package com.p3achb0t.api.interfaces

interface BufferedFile {
    fun getAccessFile(): AccessFile
    fun getCapacity(): Long
    fun getReadBuffer(): ByteArray
    fun getWriteBuffer(): ByteArray
    fun get__p(): Int
    fun get__t(): Int
    fun get__e(): Long
    fun get__l(): Long
    fun get__n(): Long
    fun get__w(): Long
    fun get__z(): Long
}
