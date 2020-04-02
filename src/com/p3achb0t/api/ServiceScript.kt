package com.p3achb0t.api

abstract class ServiceScript: SuperScript() {
    abstract suspend fun loop()
    open fun start() {}
    open fun stop() {}
}