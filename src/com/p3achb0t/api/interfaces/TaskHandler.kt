package com.p3achb0t.api.interfaces

interface TaskHandler {
    fun getCurrent(): Task
    fun getIsClosed(): Boolean
    fun getTask0(): Task
    fun getThread(): Any
}
