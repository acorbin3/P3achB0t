package com.p3achb0t._runelite_interfaces

interface TextureProvider {
    fun getBrightness0(): Double
    fun getCapacity(): Int
    fun getDeque(): NodeDeque
    fun getIndexCache(): AbstractIndexCache
    fun getRemaining(): Int
    fun getTextureSize(): Int
    fun getTextures(): Array<Texture>
}
