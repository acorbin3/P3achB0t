package com.p3achb0t.api

import java.awt.Graphics

abstract class DebugScript: SuperScript() {

    abstract fun draw(g: Graphics)
    abstract fun start()
    abstract fun stop()
}