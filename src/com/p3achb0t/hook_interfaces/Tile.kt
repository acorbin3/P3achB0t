package com.p3achb0t.hook_interfaces

interface Tile : Node {
    fun get_boundary(): Any
    fun get_floor(): Any
    fun get_itemLayer(): Any
    fun get_objects(): Any
    fun get_plane(): Int
    fun get_wall(): Any
    fun get_x(): Int
    fun get_y(): Int
}
