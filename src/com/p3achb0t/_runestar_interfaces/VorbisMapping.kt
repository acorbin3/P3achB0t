package com.p3achb0t._runestar_interfaces

interface VorbisMapping {
	fun getMappingMux(): Int
	fun getSubmapFloor(): IntArray
	fun getSubmapResidue(): IntArray
	fun getSubmaps(): Int
}
