package com.p3achb0t._runestar_interfaces

interface Scene {
	fun getMinPlane(): Int
	fun getPlanes(): Int
    fun getTempScenery(): Array<Scenery>
    fun getTempSceneryCount(): Int
	fun getTileHeights(): Array<Array<IntArray>>
	fun getTiles(): Array<Array<Array<Tile>>>
	fun getXSize(): Int
	fun getYSize(): Int
    fun get__bh(): Array<IntArray>
    fun get__bz(): Array<IntArray>
    fun get__d(): Array<Array<IntArray>>
}
