package com.p3achb0t.hook_interfaces

interface AnimatedObject : Renderable {
    fun getAnimationDelay(): Int
    fun getAnimationFrame(): Int
    fun getClickType(): Int
    fun getAnimatedObject_id(): Int
    fun getOrientation(): Int
    fun getPlane(): Int
    fun getSequence(): Sequence
    fun getX(): Int
    fun getY(): Int
}
