package com.p3achb0t.api.interfaces

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
    fun get__ak(): Boolean
    fun get__af(): Int
    fun get__az(): Int
    fun get__aa(): Boolean
    fun focusLost(arg0: Boolean)
}
