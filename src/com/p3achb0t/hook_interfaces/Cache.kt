package com.p3achb0t.hook_interfaces

interface Cache {
    fun getCacheNode(): CacheNode
    fun getHashTable(): IterableHashTable
    fun getQueue(): Queue
    fun getRemaining(): Int
    fun getSize(): Int
}
