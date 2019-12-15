package com.p3achb0t._runestar_interfaces

interface NetSocket : AbstractSocket {
    fun getArray(): ByteArray
    fun getExceptionWriting(): Boolean
    fun getInputStream(): Any
    fun getIsClosed(): Boolean
    fun getOutputStream(): Any
    fun getSocket(): Any
    fun getTask(): Task
    fun getTaskHandler(): TaskHandler
    fun get__w(): Int
    fun get__y(): Int
    fun get__k(): Int
    fun get__x(): Int
}
