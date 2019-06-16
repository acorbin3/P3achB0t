package com.p3achb0t.hook_interfaces

interface GameObject {
    fun getFlags(): Int
    fun getHeight(): Int
    fun getId(): Long
    fun getOffsetX(): Int
    fun getOffsetY(): Int
    fun getOrientation(): Int
    fun getPlane(): Int
    fun getRelativeX(): Int
    fun getRelativeY(): Int
    fun getRenderable(): Renderable
    fun getX(): Int
    fun getY(): Int
}
