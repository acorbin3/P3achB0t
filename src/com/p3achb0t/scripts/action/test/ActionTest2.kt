package com.p3achb0t.scripts.action.test

import com.p3achb0t.api.ActionScript
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.utils.Script
import java.awt.Color
import java.awt.Graphics

@ScriptManifest(Script.ACTION,"ActionTest2","Løhde", "0.1")
class ActionTest2 : ActionScript() {

    var isPaused = false
    var isStarted = false
    var counter = 0

    override suspend fun loop() {
        counter++
    }

    override fun draw(g: Graphics) {
        g.color = Color.cyan
        g.drawString("ActionTest2 isPaused: $isPaused, isStarted $isStarted", 50,50)
        g.drawString("Counter: $counter", 50,70)

    }

    override fun start() {
        isStarted = true
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
}