package com.p3achb0t.api.interfaces

interface Wall {
    fun getEntity1(): Entity
    fun getEntity2(): Entity
    fun getFlags(): Int
    fun getOrientationA(): Int
    fun getOrientationB(): Int
    fun getTag(): Long
    fun getTileHeight(): Int
    fun getX(): Int
    fun getY(): Int
}
