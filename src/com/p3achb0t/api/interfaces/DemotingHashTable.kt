package com.p3achb0t.api.interfaces

interface DemotingHashTable {
    fun getHashTable(): IterableNodeHashTable
    fun getQueue(): IterableDualNodeQueue
    fun get__n(): Int
    fun get__v(): Int
}
