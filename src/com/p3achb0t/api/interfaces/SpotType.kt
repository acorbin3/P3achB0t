package com.p3achb0t.api.interfaces

interface SpotType : DualNode {
    fun getAmbient(): Int
    fun getContrast(): Int
    fun getId(): Int
    fun getModel(): Int
    fun getOrientation(): Int
    fun getRecol_d(): ShortArray
    fun getRecol_s(): ShortArray
    fun getResizeh(): Int
    fun getResizev(): Int
    fun getRetex_d(): ShortArray
    fun getRetex_s(): ShortArray
    fun getSequence(): Int
}
