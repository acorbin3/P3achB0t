package com.p3achb0t.api.interfaces

interface WorldMapLabel {
    fun getHeight(): Int
    fun getSize(): WorldMapLabelSize
    fun getText(): String
    fun getWidth(): Int
}
