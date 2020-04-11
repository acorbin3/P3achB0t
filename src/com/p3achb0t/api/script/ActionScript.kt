package com.p3achb0t.api.script

abstract class ActionScript(val tasks: ArrayList<LeafTask> = ArrayList(),var currentJob: String = ""): SuperScript() {
    open suspend fun loop(){
        tasks.forEach {
            if (it.isValidToRun()) {
                currentJob = it.javaClass.name.split(".").last()
                logger.debug("Running: ${it.javaClass.name}")
                it.execute()
                logger.debug("Completed: ${it.javaClass.name}")
            }
        }
    }

    open fun pause() {}
    open fun resume() {}
}