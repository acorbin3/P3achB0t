package com.p3achb0t.api.script

import com.p3achb0t.api.Context
import com.p3achb0t.api.utils.Logging

/*This class is responsible for have a condition to be evaluated and upon true the base set of code
* in execute would be run*/
abstract class LeafTask(val ctx: Context): Logging() {

    abstract suspend fun isValidToRun(): Boolean
    abstract suspend fun execute()
}