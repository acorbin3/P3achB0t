package com.p3achb0t.api.interfaces

interface WorldMapEvent {
    fun getCoord1(): Coord
    fun getCoord2(): Coord
    fun getMapElement(): Int
}
