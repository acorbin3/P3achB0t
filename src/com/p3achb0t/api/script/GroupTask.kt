package com.p3achb0t.api.script

import com.p3achb0t.api.Context

abstract class GroupTask(ctx: Context, val children: ArrayList<LeafTask>): LeafTask(ctx) {

    override suspend fun execute() {
        children.forEach {
            if(it.isValidToRun()){
                it.execute()
            }
        }
    }
}