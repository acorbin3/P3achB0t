package com.p3achb0t._runelite_interfaces

interface IndexStoreAction : Node {
    fun getData(): ByteArray
    fun getIndexCache(): IndexCache
    fun getIndexStore(): IndexStore
    fun getType(): Int
}
