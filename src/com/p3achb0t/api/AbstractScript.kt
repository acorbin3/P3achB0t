package com.p3achb0t.api

import java.awt.Graphics

abstract class AbstractScript {

    protected lateinit var ctx: Context

    fun initialize(client: Any) {
        ctx = Context(client)
    }

    abstract suspend fun loop()

    abstract suspend fun start()

    abstract fun stop()

    open fun draw(g: Graphics) {}
}