package com.p3achb0t._runestar_interfaces

interface Tile : Node {
    fun getDrawPrimary(): Boolean
    fun getDrawScenery(): Boolean
    fun getDrawSceneryEdges(): Int
    fun getDrawSecondary(): Boolean
    fun getFloorDecoration(): FloorDecoration
    fun getLinkedBelowTile(): Tile
    fun getMinPlane(): Int
    fun getModel(): TileModel
    fun getObjStack(): ObjStack
    fun getOriginalPlane(): Int
    fun getPaint(): TilePaint
    fun getPlane(): Int
    fun getScenery(): Array<Scenery>
    fun getSceneryCount(): Int
    fun getSceneryEdgeMask(): Int
    fun getSceneryEdgeMasks(): IntArray
    fun getWall(): Wall
    fun getWallDecoration(): WallDecoration
    fun getX(): Int
    fun getY(): Int
    fun get__m(): Int
    fun get__s(): Int
    fun get__t(): Int
}
