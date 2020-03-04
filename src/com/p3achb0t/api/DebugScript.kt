package com.p3achb0t.api

import java.awt.Graphics

abstract class DebugScript {
    lateinit var ctx: Context
    var scriptName = ""

    fun initialize(ctx: Context) {
        this.ctx = ctx
    }

    abstract fun draw(g: Graphics)
}