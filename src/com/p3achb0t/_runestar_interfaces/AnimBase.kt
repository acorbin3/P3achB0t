package com.p3achb0t._runestar_interfaces

interface AnimBase: Node{
	fun getId(): Int
	fun getTransformCount(): Int
	fun getTransformLabels(): Array<IntArray>
	fun getTransformTypes(): IntArray
}
