package com.p3achb0t._runestar_interfaces

interface Task {
	fun getIntArgument(): Int
	fun getNext(): Task
	fun getObjectArgument(): Any
	fun getResult(): Any
	fun getStatus(): Int
	fun getType(): Int
}
