package com.p3achb0t.api.interfaces

interface MilliClock : Clock {
    fun get__j(): Int
    fun get__t(): Int
    fun get__v(): Int
    fun get__x(): Int
    fun get__w(): Long
    fun get__h(): Array<Long>
}
