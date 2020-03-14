package com.p3achb0t.api.interfaces

interface CollisionMap {
    fun getFlags(): Array<IntArray>
    fun getXInset(): Int
    fun getXSize(): Int
    fun getYInset(): Int
    fun getYSize(): Int
}
