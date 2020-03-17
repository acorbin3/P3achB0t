package com.p3achb0t.api

import java.awt.Graphics

abstract class AbstractScript {

    lateinit var ctx: Context

    abstract suspend fun loop()
    abstract suspend fun start()
    abstract fun stop()
    open fun draw(g: Graphics) {}
}