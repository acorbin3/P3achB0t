package com.p3achb0t.hook_interfaces

interface Projectile : Renderable {
    fun get_endCycle(): Int
    fun get_floorLevel(): Int
    fun get_height(): Int
    fun get_heightOffset(): Any
    fun get_projectileID(): Int
    fun get_moving(): Any
    fun get_originX(): Int
    fun get_originY(): Int
    fun get_rotationX(): Int
    fun get_rotationY(): Int
    fun get_sequence(): Any
    fun get_slope(): Int
    fun get_speedX(): Any
    fun get_speedY(): Any
    fun get_speedZ(): Any
    fun get_startCycle(): Int
    fun get_targetDistance(): Int
    fun get_targetIndex(): Int
    fun get_unknown1(): Any
    fun get_unknown2(): Any
    fun get_unknown3(): Int
    fun get_x(): Any
    fun get_y(): Any
}
