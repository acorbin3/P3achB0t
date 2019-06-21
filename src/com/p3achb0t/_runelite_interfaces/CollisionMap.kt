package com.p3achb0t._runelite_interfaces

interface CollisionMap {
    fun getFlags(): Array<Array<Int>>
    fun getXInset(): Int
    fun getXSize(): Int
    fun getYInset(): Int
    fun getYSize(): Int
}
