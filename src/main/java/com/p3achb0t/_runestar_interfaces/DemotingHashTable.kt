package com.p3achb0t._runestar_interfaces

interface DemotingHashTable {
	fun getCapacity(): Int
	fun getHashTable(): IterableNodeHashTable
	fun getQueue(): IterableDualNodeQueue
	fun getRemaining(): Int
}
