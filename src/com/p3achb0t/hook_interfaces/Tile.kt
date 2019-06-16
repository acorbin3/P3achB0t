package com.p3achb0t.hook_interfaces

interface Tile : Node {
    fun getBoundary(): BoundaryObject
    fun getFloor(): FloorObject
    fun getItemLayer(): ItemLayer
    fun getObjects(): Array<GameObject>
    fun getPlane(): Int
    fun getWall(): WallObject
    fun getX(): Int
    fun getY(): Int
}
