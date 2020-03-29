package com.p3achb0t.api.interfaces

import java.awt.event.KeyEvent

interface Keyboard {

    fun inputBlocked(value: Boolean)
    fun inputBlocked(): Boolean
    fun sendEvent(e: KeyEvent)
}