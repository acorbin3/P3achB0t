package com.p3achb0t.api.script

import com.p3achb0t.api.Context

/*
* This class is responsible for grouping multiple LeafTasks in a collection where the children would only be run
* if the Group Task was evaluated to be true*/
abstract class GroupTask(ctx: Context, val children: ArrayList<LeafTask>): LeafTask(ctx) {

    override suspend fun execute() {
        children.forEach {
            if(it.isValidToRun()){
                logger.debug("Child - Running: ${it.javaClass.name}")
                it.execute()
                logger.debug("Child - Completed: ${it.javaClass.name}")
            }
        }
    }
}