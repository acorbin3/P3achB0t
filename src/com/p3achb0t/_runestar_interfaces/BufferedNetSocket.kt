package com.p3achb0t._runestar_interfaces

interface BufferedNetSocket: AbstractSocket{
	fun getSink(): BufferedSink
	fun getSocket(): Any
	fun getSource(): BufferedSource
}
