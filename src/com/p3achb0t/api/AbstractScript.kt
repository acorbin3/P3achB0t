package com.p3achb0t.api

import com.p3achb0t.api.utils.Logging
import java.awt.Graphics

abstract class AbstractScript: Logging() {

    lateinit var ctx: Context
    var validate: Boolean = false

    fun initialize(ctx: Context) {
        this.ctx = ctx
    }

    abstract suspend fun loop()

    abstract suspend fun start()

    abstract fun stop()

    open fun draw(g: Graphics) {}
}