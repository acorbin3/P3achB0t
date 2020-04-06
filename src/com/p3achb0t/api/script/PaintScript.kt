package com.p3achb0t.api.script

import java.awt.Graphics

abstract class PaintScript: SuperScript() {
    abstract fun draw(g: Graphics)
    open fun start() {}
    open fun stop() {}
}