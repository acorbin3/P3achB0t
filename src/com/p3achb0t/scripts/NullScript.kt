package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript

class NullScript : AbstractScript() {
    override fun loop() {
        print("*")
    }

    override fun start() {
        println("Started")
    }

    override fun stop() {
        println()
        println("Stopped")
    }
}