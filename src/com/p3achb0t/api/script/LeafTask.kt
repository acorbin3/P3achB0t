package com.p3achb0t.api.script

import com.p3achb0t.api.Context
import com.p3achb0t.api.utils.Logging

abstract class LeafTask(val ctx: Context): Logging() {

    abstract suspend fun isValidToRun(): Boolean
    abstract suspend fun execute()
}