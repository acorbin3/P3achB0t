package com.p3achb0t.hook_interfaces

interface Region {
    fun getFocusedX(): Int
    fun getFocusedY(): Int
    fun getGameObjects(): Array<GameObject>
    fun getRightClickWalk(): Boolean
    fun getTiles(): Array<Array<Array<Tile>>>
    fun getVisibilityTilesMap(): Array<Array<Array<BooleanArray>>>
    fun getVisibleTiles(): Array<BooleanArray>
}
