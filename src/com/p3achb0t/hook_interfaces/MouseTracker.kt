package com.p3achb0t.hook_interfaces

interface MouseTracker {
    fun getLength(): Int
    fun getLock(): Any
    fun getTracking(): Boolean
    fun getXCoordinates(): Any
    fun getYCoordinates(): Any
}
