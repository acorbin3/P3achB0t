package com.p3achb0t.scripts

abstract class Job {
    abstract suspend fun isValidToRun(): Boolean
    abstract suspend fun execute()
}