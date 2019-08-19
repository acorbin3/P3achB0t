package com.p3achb0t._runestar_interfaces

interface AbstractFont: Rasterizer2D{
	fun getAdvances(): IntArray
	fun getAscent(): Int
	fun getHeights(): IntArray
	fun getKerning(): ByteArray
	fun getLeftBearings(): IntArray
	fun getMaxAscent(): Int
	fun getMaxDescent(): Int
	fun getPixels(): Array<ByteArray>
	fun getTopBearings(): IntArray
	fun getWidths(): IntArray
}
