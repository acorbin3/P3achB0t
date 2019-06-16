package com.p3achb0t.hook_interfaces

interface Cache {
    fun getCacheNode(): CacheNode
    fun getHashTable(): HashTable
    fun getQueue(): Queue
    fun getRemaining(): Int
    fun getSize(): Int
}
