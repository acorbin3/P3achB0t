package com.p3achb0t._runelite_interfaces

interface WorldMapEvent {
    fun getCoord1(): TileLocation
    fun getCoord2(): TileLocation
    fun getMapElement(): Int
}
