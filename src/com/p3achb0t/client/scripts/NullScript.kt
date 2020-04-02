package com.p3achb0t.client.scripts

import com.p3achb0t.api.ActionScript
import kotlinx.coroutines.delay

class NullScript : ActionScript() {
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