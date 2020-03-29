package com.p3achb0t.api.interfaces

interface NanoClock : Clock {
    fun getLastTimeNano(): Long
}
