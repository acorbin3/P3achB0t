package com.p3achb0t.api.interfaces

interface NetSocket : AbstractSocket {
    fun getArray(): ByteArray
    fun getExceptionWriting(): Boolean
    fun getInputStream(): Any
    fun getIsClosed(): Boolean
    fun getOutputStream(): Any
    fun getSocket(): Any
    fun getTask(): Task
    fun getTaskHandler(): TaskHandler
    fun get__b(): Int
    fun get__v(): Int
    fun get__i(): Int
    fun get__x(): Int
}
