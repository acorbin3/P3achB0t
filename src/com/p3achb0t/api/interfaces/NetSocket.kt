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
    fun get__e(): Int
    fun get__q(): Int
    fun get__b(): Int
    fun get__s(): Int
}
