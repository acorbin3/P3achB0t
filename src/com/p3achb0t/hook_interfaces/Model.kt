package com.p3achb0t.hook_interfaces

interface Model : Renderable {
    fun getIndicesLength(): Int
    fun getIndicesX(): IntArray
    fun getIndicesY(): IntArray
    fun getIndicesZ(): IntArray
    fun getOnCursorUIDs(): Array<Long>
    fun getUidCount(): Int
    fun getVectorSkin(): Array<IntArray>
    fun getVerticesLength(): Int
    fun getVerticesX(): IntArray
    fun getVerticesY(): IntArray
    fun getVerticesZ(): IntArray
}
