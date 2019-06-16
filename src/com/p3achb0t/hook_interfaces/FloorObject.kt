package com.p3achb0t.hook_interfaces

interface FloorObject {
    fun getId(): Long
    fun getPlane(): Int
    fun getRenderable(): Renderable
    fun getX(): Int
    fun getY(): Int
}
