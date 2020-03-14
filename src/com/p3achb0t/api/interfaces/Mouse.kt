package com.p3achb0t.api.interfaces

import java.awt.event.MouseEvent

interface Mouse {

    fun getX(): Int
    fun getY(): Int
    fun inputBlocked(value: Boolean)
    fun inputBlocked(): Boolean
    fun sendEvent(e: MouseEvent)
}