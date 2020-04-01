package com.p3achb0t.api

abstract class ServiceScript: SuperScript() {

    abstract suspend fun loop()
    abstract fun start()
    abstract fun stop()
}