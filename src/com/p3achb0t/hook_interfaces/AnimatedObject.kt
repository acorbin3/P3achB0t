package com.p3achb0t.hook_interfaces

interface AnimatedObject : Renderable {
    fun get_animationDelay(): Int
    fun get_animationFrame(): Int
    fun get_clickType(): Int
    fun get_animatedObjectID(): Int
    fun get_orientation(): Int
    fun get_plane(): Int
    fun get_sequence(): Any
    fun get_x(): Int
    fun get_y(): Int
}
