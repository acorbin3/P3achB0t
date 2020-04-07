package com.p3achb0t.api.script

import com.p3achb0t.scripts.Task

abstract class ActionScript: SuperScript() {
    val tasks = ArrayList<Task>()
    open suspend fun loop(){
        tasks.forEach {
            if (it.isValidToRun()) {
                logger.debug("Running: ${it.javaClass.name}")
                it.execute()
                logger.debug("Completed: ${it.javaClass.name}")
            }
        }
    }

    open fun pause() {}
    open fun resume() {}
}