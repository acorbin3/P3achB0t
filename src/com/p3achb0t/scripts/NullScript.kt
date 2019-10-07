package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript

class NullScript : AbstractScript() {
    override suspend fun loop() {
        print("*")
    }

    override suspend fun start() {
        println("Started")
    }

    override suspend fun stop() {
        println()
        println("Stopped")
    }
}