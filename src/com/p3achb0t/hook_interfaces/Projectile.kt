package com.p3achb0t.hook_interfaces

interface Projectile : Renderable {
    fun getEndCycle(): Int
    fun getFloorLevel(): Int
    fun getHeight(): Int
    fun getHeightOffset(): Double
    fun getProjectile_id(): Int
    fun getMoving(): Boolean
    fun getOriginX(): Int
    fun getOriginY(): Int
    fun getRotationX(): Int
    fun getRotationY(): Int
    fun getSequence(): Sequence
    fun getSlope(): Int
    fun getSpeedX(): Double
    fun getSpeedY(): Double
    fun getSpeedZ(): Double
    fun getStartCycle(): Int
    fun getTargetDistance(): Int
    fun getTargetIndex(): Int
    fun getUnknown1(): Int
    fun getUnknown2(): Int
    fun getUnknown3(): Int
    fun getX(): Double
    fun getY(): Double
}
