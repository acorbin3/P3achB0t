package com.p3achb0t.api.script

import com.p3achb0t.scripts.Task
import java.awt.Graphics

abstract class ActionScript: SuperScript() {
    val tasks = ArrayList<Task>()
    open suspend fun loop(){
        tasks.forEach {
            if (it.isValidToRun()) {
                println("Running: ${it.javaClass.name}")
                it.execute()
                println("Completed: ${it.javaClass.name}")
            }
        }
    }
    open fun draw(g: Graphics) {}
    open fun start() {}
    open fun stop() {}
    open fun pause() {}
    open fun resume() {}
}