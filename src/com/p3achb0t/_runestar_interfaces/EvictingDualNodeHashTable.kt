package com.p3achb0t._runestar_interfaces

interface EvictingDualNodeHashTable {
	fun getCapacity(): Int
	fun getDeque(): IterableDualNodeQueue
	fun getHashTable(): IterableNodeHashTable
	fun getRemainingCapacity(): Int
	fun get__s(): DualNode
}
