package com.p3achb0t._runestar_interfaces

interface MapElementType: DualNode{
	fun getCategory(): Int
	fun getIop(): Array<String>
	fun getLabel(): String
	fun getLabelcolor(): Int
	fun getLabelsize(): Int
	fun getOpbase(): String
	fun getSprite1(): Int
	fun getSprite2(): Int
	fun get__l(): ByteArray
	fun get__b(): Int
	fun get__e(): Int
	fun get__g(): Int
	fun get__p(): Int
	fun get__w(): IntArray
	fun get__y(): IntArray
	fun get__u(): Int
}
