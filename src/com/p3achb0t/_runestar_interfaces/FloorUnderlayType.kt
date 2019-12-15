package com.p3achb0t._runestar_interfaces

interface FloorUnderlayType : DualNode {
    fun getHue(): Int
    fun getHueMultiplier(): Int
    fun getLightness(): Int
    fun getRgb(): Int
    fun getSaturation(): Int
}
