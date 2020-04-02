package com.p3achb0t.scripts.action.test

import com.p3achb0t.api.ActionScript
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.utils.Script
import java.awt.Color
import java.awt.Graphics

@ScriptManifest(Script.ACTION,"ActionTest","Løhde", "0.1")
class ActionTest : ActionScript() {

    var isPaused = false
    var isStarted = false
    var counter = 0
    var lastMessage = ""

    override suspend fun loop() {
        counter++
    }

    override fun draw(g: Graphics) {
        g.color = Color.cyan
        g.drawString("ActionTest isPaused: $isPaused, isStarted $isStarted", 50,50)
        g.drawString("Counter: $counter", 50,70)
        g.drawString("Last Message: $lastMessage", 50,90)
    }

    override fun start() {
        isStarted = true
        ctx.ipc.subscribe(ctx.ipc.uuid, ::callback)
        ctx.ipc.subscribe("Login Channel", ::callback)
        ctx.ipc.subscribe(":---O", ::callback)
    }

    override fun stop() {
        isStarted = false
    }

    override fun pause() {
        isPaused = true
    }

    override fun resume() {
        isPaused = false
    }

    private fun callback(channel: String, message: String) {
        lastMessage = message
    }
}