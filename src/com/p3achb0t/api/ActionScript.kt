package com.p3achb0t.api

import java.awt.Graphics

abstract class ActionScript: SuperScript() {
    abstract suspend fun loop()
    open fun draw(g: Graphics) {}
    open fun start() {}
    open fun stop() {}
    open fun pause() {}
    open fun resume() {}

}