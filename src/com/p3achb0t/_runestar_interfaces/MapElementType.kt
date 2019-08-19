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
	fun get__o(): ByteArray
	fun get__a(): Int
	fun get__g(): Int
	fun get__v(): Int
	fun get__y(): Int
	fun get__h(): IntArray
	fun get__r(): IntArray
	fun get__k(): Int
}
