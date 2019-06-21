package com.p3achb0t._runelite_interfaces

interface IndexCache : AbstractIndexCache {
    fun getIndex(): Int
    fun getIndexReferenceCrc(): Int
    fun getIndexReferenceVersion(): Int
    fun getIndexStore(): IndexStore
    fun getReferenceStore(): IndexStore
    fun getValidArchives(): Array<Boolean>
    fun get__ag(): Boolean
    fun get__ac(): Int
    fun get__v(): Boolean
}
