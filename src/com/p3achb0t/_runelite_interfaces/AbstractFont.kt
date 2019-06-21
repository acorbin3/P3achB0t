package com.p3achb0t._runelite_interfaces

interface AbstractFont : Rasterizer2D {
    fun getAdvances(): Array<Int>
    fun getAscent(): Int
    fun getHeights(): Array<Int>
    fun getKerning(): Array<Byte>
    fun getLeftBearings(): Array<Int>
    fun getMaxAscent(): Int
    fun getMaxDescent(): Int
    fun getPixels(): Array<Array<Byte>>
    fun getTopBearings(): Array<Int>
    fun getWidths(): Array<Int>
}
