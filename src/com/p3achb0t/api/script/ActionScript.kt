package com.p3achb0t.api.script

abstract class ActionScript(val tasks: ArrayList<LeafTask> = ArrayList(),var currentJob: String = "", var currentJobSuspendable: Boolean = true): SuperScript() {
    open suspend fun loop(){
        tasks.forEach {
            if (it.isValidToRun()) {
                currentJob = it.javaClass.name.split(".").last()
                logger.debug("Running: ${it.javaClass.name}")
                if(it is GroupTask){
                    it.children.forEach {child ->
                        if(child.isValidToRun()){
                            logger.debug("Child - Running: ${it.javaClass.name}")
                            currentJobSuspendable = child.canBeSuspended
                            child.execute()
                            logger.debug("Child - Completed: ${it.javaClass.name}")

                        }
                        currentJobSuspendable = true
                    }
                }else{
                    currentJobSuspendable = it.canBeSuspended
                    it.execute()
                    currentJobSuspendable = true
                }
                logger.debug("Completed: ${it.javaClass.name}")
            }
        }
    }

    open fun pause() {}
    open fun resume() {}
}