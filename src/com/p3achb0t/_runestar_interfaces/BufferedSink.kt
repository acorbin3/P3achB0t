package com.p3achb0t._runestar_interfaces

interface BufferedSink {
	fun getBuffer(): ByteArray
	fun getCapacity(): Int
	fun getException(): Any
	fun getIsClosed0(): Boolean
	fun getLimit(): Int
	fun getOutputStream(): Any
	fun getPosition(): Int
	fun getThread(): Any
}
