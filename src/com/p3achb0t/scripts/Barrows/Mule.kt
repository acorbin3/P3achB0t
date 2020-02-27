package com.p3achb0t.scripts.Barrows

import com.p3achb0t.api.Context
import com.p3achb0t.scripts.Task

class Mule(val ctx: Context) : Task(ctx.client) {
    override suspend fun isValidToRun(): Boolean {
        return false
    }


    override suspend fun execute() {


    }
}