package com.p3achb0t.hook_interfaces

interface Region {
    fun get_focusedX(): Int
    fun get_focusedY(): Int
    fun get_gameObjects(): Any
    fun get_rightClickWalk(): Any
    fun get_tiles(): Any
    fun get_visibilityTilesMap(): Any
    fun get_visibleTiles(): Any
}
