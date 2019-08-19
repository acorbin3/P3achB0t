package com.p3achb0t._runestar_interfaces

interface PcmStreamMixer: PcmStream{
	fun getSubStreams(): NodeDeque
	fun get__k(): Int
	fun get__p(): Int
	fun get__e(): NodeDeque
}
