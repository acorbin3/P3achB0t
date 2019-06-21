package com.p3achb0t._runelite_interfaces

interface GroundItemPile {
    fun getFirst(): Entity
    fun getHeight(): Int
    fun getSecond(): Entity
    fun getTag(): Long
    fun getThird(): Entity
    fun getTileHeight(): Int
    fun getX(): Int
    fun getY(): Int
}
