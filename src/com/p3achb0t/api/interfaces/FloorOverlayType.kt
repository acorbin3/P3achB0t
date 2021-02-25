package com.p3achb0t.api.interfaces

interface FloorOverlayType : DualNode {
    fun getHue(): Int
    fun getHue2(): Int
    fun getLightness(): Int
    fun getLightness2(): Int
    fun getRgb(): Int
    fun getRgb2(): Int
    fun getSaturation(): Int
    fun getSaturation2(): Int
    fun getTexture(): Int
    fun get__y(): Boolean
}
