package com.p3achb0t._runelite_interfaces

interface MouseRecorder {
    fun getIndex(): Int
    fun getIsRunning(): Boolean
    fun getLock(): Any
    fun getMillis(): Array<Long>
    fun getXs(): Array<Int>
    fun getYs(): Array<Int>
}
