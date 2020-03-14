package com.p3achb0t.api.interfaces

interface Sprite : Rasterizer2D {
    fun getHeight(): Int
    fun getPixels(): IntArray
    fun getSubHeight(): Int
    fun getSubWidth(): Int
    fun getWidth(): Int
    fun getXOffset(): Int
    fun getYOffset(): Int
}
