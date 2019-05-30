package com.p3achb0t.hook_interfaces

interface Model : Renderable {
    fun get_indicesLength(): Int
    fun get_indicesX(): ArrayList<Int>
    fun get_indicesY(): ArrayList<Int>
    fun get_indicesZ(): ArrayList<Int>
    fun get_onCursorUIDs(): ArrayList<Int>
    fun get_uidCount(): Int
    fun get_vectorSkin(): ArrayList<ArrayList<Int>>
    fun get_verticesLength(): Int
    fun get_verticesX(): ArrayList<Int>
    fun get_verticesY(): ArrayList<Int>
    fun get_verticesZ(): ArrayList<Int>
}
