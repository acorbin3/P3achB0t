package com.p3achb0t._runestar_interfaces

interface Tile: Node{
	fun getBoundaryObject(): BoundaryObject
	fun getDrawGameObjectEdges(): Int
	fun getDrawGameObjects(): Boolean
	fun getDrawPrimary(): Boolean
	fun getDrawSecondary(): Boolean
	fun getFloorDecoration(): FloorDecoration
	fun getGameObjectEdgeMasks(): IntArray
	fun getGameObjects(): Array<GameObject>
	fun getGameObjectsCount(): Int
	fun getGameObjectsEdgeMask(): Int
	fun getLinkedBelowTile(): Tile
	fun getMinPlane(): Int
	fun getModel(): TileModel
	fun getObjStack(): ObjStack
	fun getOriginalPlane(): Int
	fun getPaint(): TilePaint
	fun getPlane(): Int
	fun getWallDecoration(): WallDecoration
	fun getX(): Int
	fun getY(): Int
	fun get__h(): Int
	fun get__o(): Int
	fun get__t(): Int
}
