package com.p3achb0t.scripts

import com.p3achb0t.api.interfaces.Client

abstract class Task(val client: Client) {
    abstract suspend fun isValidToRun(): Boolean
    abstract suspend fun execute()
}