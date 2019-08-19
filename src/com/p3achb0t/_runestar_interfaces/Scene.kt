package com.p3achb0t._runestar_interfaces

interface Scene {
	fun getMinPlane(): Int
	fun getPlanes(): Int
	fun getTempGameObjects(): Array<GameObject>
	fun getTempGameObjectsCount(): Int
	fun getTileHeights(): Array<Array<IntArray>>
	fun getTiles(): Array<Array<Array<Tile>>>
	fun getXSize(): Int
	fun getYSize(): Int
	fun get__bm(): Array<IntArray>
	fun get__bn(): Array<IntArray>
	fun get__f(): Array<Array<IntArray>>
}
