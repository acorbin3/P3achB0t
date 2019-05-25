package com.p3achb0t.hook_interfaces

interface GameObject {
    fun get_flags(): Int
    fun get_height(): Int
    fun get_id(): Int
    fun get_offsetX(): Int
    fun get_offsetY(): Int
    fun get_orientation(): Int
    fun get_plane(): Int
    fun get_relativeX(): Int
    fun get_relativeY(): Int
    fun get_renderable(): Any
    fun get_x(): Int
    fun get_y(): Int
}
