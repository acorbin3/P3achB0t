package com.p3achb0t.scripts.Vorkath

import com.p3achb0t.api.Context
import com.p3achb0t.scripts.Task
import com.p3achb0t.scripts.VorkathMain

class DeathWalk(val ctx: Context) : Task(ctx.client) {
    override suspend fun isValidToRun(): Boolean {
        return false
    }


    override suspend fun execute() {


    }
}