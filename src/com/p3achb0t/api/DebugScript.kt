package com.p3achb0t.api

import java.awt.Graphics

abstract class DebugScript {
    protected lateinit var ctx: Context

    fun initialize(client: Any) {
        ctx = Context(client)
    }

    abstract fun draw(g: Graphics)
}