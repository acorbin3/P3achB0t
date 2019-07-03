package com.p3achb0t._runelite_interfaces

interface SpriteMask : DualNode {
    fun getHeight(): Int
    fun getWidth(): Int
    fun getXStarts(): IntArray
    fun getXWidths(): IntArray
}
