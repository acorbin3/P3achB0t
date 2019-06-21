package com.p3achb0t._runelite_interfaces

interface UnderlayDefinition : DualNode {
    fun getHue(): Int
    fun getHueMultiplier(): Int
    fun getLightness(): Int
    fun getRgb(): Int
    fun getSaturation(): Int
}
