package com.p3achb0t._runelite_interfaces

interface Texture : Node {
    fun getAnimationDirection(): Int
    fun getAnimationSpeed(): Int
    fun getInt1(): Int
    fun getIsLoaded(): Boolean
    fun getPixels(): Array<Int>
    fun getRecords(): Array<Int>
    fun get__u(): Boolean
    fun get__e(): Array<Int>
    fun get__l(): Array<Int>
    fun get__x(): Array<Int>
}
