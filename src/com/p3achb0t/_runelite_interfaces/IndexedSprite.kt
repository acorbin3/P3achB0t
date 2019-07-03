package com.p3achb0t._runelite_interfaces

interface IndexedSprite : Rasterizer2D {
    fun getHeight(): Int
    fun getPalette(): IntArray
    fun getPixels(): ByteArray
    fun getSubHeight(): Int
    fun getSubWidth(): Int
    fun getWidth(): Int
    fun getXOffset(): Int
    fun getYOffset(): Int
}
