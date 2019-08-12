package com.p3achb0t._runestar_interfaces

interface TaskHandler {
	fun getCurrent(): Task
	fun getIsClosed(): Boolean
	fun getTask0(): Task
	fun getThread(): Any
}
