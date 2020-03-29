package com.p3achb0t.api

abstract class BackgroundScript {
    lateinit var ctx: Context

    abstract suspend fun loop()
    abstract suspend fun start()
    abstract fun stop()
}