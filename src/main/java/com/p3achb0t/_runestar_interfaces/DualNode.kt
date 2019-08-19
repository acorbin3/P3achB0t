package com.p3achb0t._runestar_interfaces

interface DualNode: Node{
	fun getKeyDual(): Long
	fun getNextDual(): DualNode
	fun getPreviousDual(): DualNode
}
