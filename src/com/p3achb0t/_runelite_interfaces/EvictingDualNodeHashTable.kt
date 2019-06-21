package com.p3achb0t._runelite_interfaces

interface EvictingDualNodeHashTable {
    fun getCapacity(): Int
    fun getDeque(): DualNodeDeque
    fun getHashTable(): NodeHashTable
    fun getRemainingCapacity(): Int
    fun get__m(): DualNode
}
