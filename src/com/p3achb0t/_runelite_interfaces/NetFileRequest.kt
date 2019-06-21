package com.p3achb0t._runelite_interfaces

interface NetFileRequest : DualNode {
    fun getCrc(): Int
    fun getIndexCache(): IndexCache
    fun getPadding(): Byte
}
