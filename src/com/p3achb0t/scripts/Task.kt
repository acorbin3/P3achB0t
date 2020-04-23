package com.p3achb0t.scripts

import com.p3achb0t.api.interfaces.Client
import com.p3achb0t.api.utils.Logging

abstract class Task(val client: Client): Logging() {
    abstract suspend fun isValidToRun(): Boolean
    abstract suspend fun execute()}