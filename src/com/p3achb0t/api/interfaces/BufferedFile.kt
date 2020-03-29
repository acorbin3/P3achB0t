package com.p3achb0t.api.interfaces

interface BufferedFile {
    fun getAccessFile(): AccessFile
    fun getCapacity(): Long
    fun getReadBuffer(): ByteArray
    fun getWriteBuffer(): ByteArray
    fun get__w(): Int
    fun get__z(): Int
    fun get__d(): Long
    fun get__e(): Long
    fun get__p(): Long
    fun get__q(): Long
    fun get__t(): Long
}
