package com.p3achb0t.api.interfaces

interface BufferedFile {
    fun getAccessFile(): AccessFile
    fun getCapacity(): Long
    fun getReadBuffer(): ByteArray
    fun getWriteBuffer(): ByteArray
    fun get__i(): Int
    fun get__w(): Int
    fun get__d(): Long
    fun get__g(): Long
    fun get__m(): Long
    fun get__t(): Long
    fun get__x(): Long
}
