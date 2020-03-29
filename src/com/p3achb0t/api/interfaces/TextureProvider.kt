package com.p3achb0t.api.interfaces

interface TextureProvider {
    fun getArchive(): AbstractArchive
    fun getBrightness0(): Double
    fun getCapacity(): Int
    fun getDeque(): NodeDeque
    fun getRemaining(): Int
    fun getTextureSize(): Int
    fun getTextures(): Array<Texture>
}
