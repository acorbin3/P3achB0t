package com.p3achb0t.api.script

import com.p3achb0t.api.script.SuperScript

abstract class ServiceScript: SuperScript() {
    abstract suspend fun loop()
    open fun start() {}
    open fun stop() {}
}