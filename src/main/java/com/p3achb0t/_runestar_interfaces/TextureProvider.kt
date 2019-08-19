package com.p3achb0t._runestar_interfaces

interface TextureProvider {
	fun getArchive(): AbstractArchive
	fun getBrightness0(): Double
	fun getCapacity(): Int
	fun getDeque(): NodeDeque
	fun getRemaining(): Int
	fun getTextureSize(): Int
	fun getTextures(): Array<Texture>
}
