package com.p3achb0t._runestar_interfaces

interface NetSocket: AbstractSocket{
	fun getArray(): ByteArray
	fun getExceptionWriting(): Boolean
	fun getInputStream(): Any
	fun getIsClosed(): Boolean
	fun getOutputStream(): Any
	fun getSocket(): Any
	fun getTask(): Task
	fun getTaskHandler(): TaskHandler
	fun get__c(): Int
	fun get__i(): Int
	fun get__m(): Int
	fun get__u(): Int
}
