package com.p3achb0t._runelite_interfaces

interface IndexStoreAction : Node {
    fun getData(): Array<Byte>
    fun getIndexCache(): IndexCache
    fun getIndexStore(): IndexStore
    fun getType(): Int
}
