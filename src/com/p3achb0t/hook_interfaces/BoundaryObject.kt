package com.p3achb0t.hook_interfaces

interface BoundaryObject {
    fun get_backUpOrientation(): Int
    fun get_backUpRenderable(): Any
    fun get_flags(): Int
    fun get_id(): Int
    fun get_orientation(): Int
    fun get_plane(): Int
    fun get_renderable(): Any
    fun get_x(): Int
    fun get_y(): Int
}
