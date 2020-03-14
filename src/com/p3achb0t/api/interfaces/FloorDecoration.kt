package com.p3achb0t.api.interfaces

interface FloorDecoration {
    fun getEntity(): Entity
    fun getFlags(): Int
    fun getTag(): Long
    fun getTileHeight(): Int
    fun getX(): Int
    fun getY(): Int
}
