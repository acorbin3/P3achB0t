package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import kotlinx.coroutines.delay

class NullScript : AbstractScript() {
    override suspend fun loop() {
        print("*")
        delay(5000)
    }

    override suspend fun start() {
        println("Started")
    }

    override fun stop() {
        println()
        println("Stopped")
    }
}