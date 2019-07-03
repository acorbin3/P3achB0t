package com.p3achb0t._runelite_interfaces

interface Scene {
    fun getMinPlane(): Int
    fun getPlanes(): Int
    fun getTempGameObjects(): Array<GameObject>
    fun getTempGameObjectsCount(): Int
    fun getTileHeights(): Array<Array<IntArray>>
    fun getTiles(): Array<Array<Array<Tile>>>
    fun getXSize(): Int
    fun getYSize(): Int
    fun get__bd(): Array<IntArray>
    fun get__bk(): Array<IntArray>
    fun get__x(): Array<Array<IntArray>>
}
