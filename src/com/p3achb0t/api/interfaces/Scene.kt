package com.p3achb0t.api.interfaces

interface Scene {
    fun getMinPlane(): Int
    fun getPlanes(): Int
    fun getTempScenery(): Array<Scenery>
    fun getTempSceneryCount(): Int
    fun getTileHeights(): Array<Array<IntArray>>
    fun getTiles(): Array<Array<Array<Tile>>>
    fun getXSize(): Int
    fun getYSize(): Int
    fun get__ba(): Array<IntArray>
    fun get__bd(): Array<IntArray>
    fun get__z(): Array<Array<IntArray>>
}
