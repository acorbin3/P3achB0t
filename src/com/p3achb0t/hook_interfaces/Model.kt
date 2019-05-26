package com.p3achb0t.hook_interfaces

interface Model : Renderable {
    fun get_indicesLength(): Int
    fun get_indicesX(): Array<Int>
    fun get_indicesY(): Array<Int>
    fun get_indicesZ(): Array<Int>
    fun get_onCursorUIDs(): Array<Int>
    fun get_uidCount(): Int
    fun get_vectorSkin(): Array<Array<Int>>
    fun get_verticesLength(): Int
    fun get_verticesX(): Array<Int>
    fun get_verticesY(): Array<Int>
    fun get_verticesZ(): Array<Int>
}
