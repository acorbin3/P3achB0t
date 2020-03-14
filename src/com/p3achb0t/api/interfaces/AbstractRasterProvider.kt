package com.p3achb0t.api.interfaces

interface AbstractRasterProvider {
    fun getHeight(): Int
    fun getPixels(): IntArray
    fun getWidth(): Int
}
