package com.p3achb0t._runelite_interfaces

interface Tile : Node {
    fun getBoundaryObject(): BoundaryObject
    fun getDrawGameObjectEdges(): Int
    fun getDrawGameObjects(): Boolean
    fun getDrawPrimary(): Boolean
    fun getDrawSecondary(): Boolean
    fun getFloorDecoration(): FloorDecoration
    fun getGameObjectEdgeMasks(): Array<Int>
    fun getGameObjects(): Array<GameObject>
    fun getGameObjectsCount(): Int
    fun getGameObjectsEdgeMask(): Int
    fun getGroundItemPile(): GroundItemPile
    fun getLinkedBelowTile(): Tile
    fun getMinPlane(): Int
    fun getModel(): TileModel
    fun getOriginalPlane(): Int
    fun getPaint(): TilePaint
    fun getPlane(): Int
    fun getWallDecoration(): WallDecoration
    fun getX(): Int
    fun getY(): Int
    fun get__b(): Int
    fun get__h(): Int
    fun get__y(): Int
}
