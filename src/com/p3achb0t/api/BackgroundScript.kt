package com.p3achb0t.api

abstract class BackgroundScript {
    lateinit var ctx: Context

    var validate: Boolean = false

    fun initialize(ctx: Context) {
        this.ctx = ctx
    }

    abstract suspend fun loop()

    abstract suspend fun start()

    abstract fun stop()
}