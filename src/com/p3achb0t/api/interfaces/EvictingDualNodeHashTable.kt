package com.p3achb0t.api.interfaces

interface EvictingDualNodeHashTable {
    fun getCapacity(): Int
    fun getDeque(): IterableDualNodeQueue
    fun getHashTable(): IterableNodeHashTable
    fun getRemainingCapacity(): Int
    fun get__c(): DualNode
}
