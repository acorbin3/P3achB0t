package com.p3achb0t._runelite_interfaces

interface GameShell {
    fun getCanvas(): Any
    fun getCanvasSetTimeMs(): Long
    fun getCanvasX(): Int
    fun getCanvasY(): Int
    fun getClipboard(): Any
    fun getContentHeight(): Int
    fun getContentHeight0(): Int
    fun getContentWidth(): Int
    fun getContentWidth0(): Int
    fun getEventQueue(): Any
    fun getFrame(): Any
    fun getHasErrored(): Boolean
    fun getIsCanvasInvalid(): Boolean
    fun getMouseWheelHandler(): MouseWheelHandler
    fun getStopTimeMs(): Long
    fun get__al(): Boolean
    fun get__aj(): Int
    fun get__aq(): Int
    fun get__ah(): Boolean
}
