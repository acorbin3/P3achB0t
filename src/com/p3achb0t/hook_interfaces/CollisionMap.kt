package com.p3achb0t.hook_interfaces

interface CollisionMap {
    fun getFlags(): Array<IntArray>
    fun getHeight(): Int
    fun getOffsetX(): Int
    fun getOffsetY(): Int
    fun getWidth(): Int
}
