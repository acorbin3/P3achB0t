package com.p3achb0t.hook_interfaces

interface WallObject {
    fun getBackUpRenderable(): Renderable
    fun getFlags(): Int
    fun getId(): Long
    fun getOrientation(): Int
    fun getPlane(): Int
    fun getRelativeX(): Int
    fun getRelativeY(): Int
    fun getRenderable(): Renderable
    fun getX(): Int
    fun getY(): Int
}
