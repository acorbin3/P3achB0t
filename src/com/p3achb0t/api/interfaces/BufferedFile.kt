package com.p3achb0t.api.interfaces

interface BufferedFile {
    fun getAccessFile(): AccessFile
    fun getCapacity(): Long
    fun getReadBuffer(): ByteArray
    fun getWriteBuffer(): ByteArray
    fun get__c(): Int
    fun get__z(): Int
    fun get__k(): Long
    fun get__m(): Long
    fun get__o(): Long
    fun get__t(): Long
    fun get__u(): Long
}
