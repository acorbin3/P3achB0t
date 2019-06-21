package com.p3achb0t._runelite_interfaces

interface IndexedSprite : Rasterizer2D {
    fun getHeight(): Int
    fun getPalette(): Array<Int>
    fun getPixels(): Array<Byte>
    fun getSubHeight(): Int
    fun getSubWidth(): Int
    fun getWidth(): Int
    fun getXOffset(): Int
    fun getYOffset(): Int
}
