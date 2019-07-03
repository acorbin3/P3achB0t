package com.p3achb0t.hook_interfaces

interface Packet : Stream {
	fun getCipher(): IsaacCipher
}
