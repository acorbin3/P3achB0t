package com.p3achb0t.api.interfaces

interface AnimFrame {
    fun getBase(): AnimBase
    fun getTransformCount(): Int
    fun getTransforms(): IntArray
    fun getTransparency(): Boolean
    fun getXs(): IntArray
    fun getYs(): IntArray
    fun getZs(): IntArray
}
