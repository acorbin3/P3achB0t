package com.p3achb0t.client.scripts

import com.p3achb0t.api.AbstractScript
import kotlinx.coroutines.delay

class NullScript : AbstractScript() {
    override suspend fun loop() {
        print("*")
        delay(5000)
    }

    override fun start() {
        println("Started")
    }

    override fun stop() {
        println()
        println("Stopped")
    }
}