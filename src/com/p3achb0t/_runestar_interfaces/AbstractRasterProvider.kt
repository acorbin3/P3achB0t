package com.p3achb0t._runestar_interfaces

interface AbstractRasterProvider {
    fun getHeight(): Int
    fun getPixels(): IntArray
    fun getWidth(): Int
}
