package com.p3achb0t.api.interfaces

interface ClientError {
    fun getCause(): Any
    fun getMessage(): String
}
