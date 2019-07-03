package com.p3achb0t.api.wrappers

import java.awt.Color
import java.awt.Graphics2D

interface Locatable {
    fun draw(g: Graphics2D)
    fun draw(g: Graphics2D, color: Color)
    fun getLocation(): Tile
    fun distanceTo(): Int
    fun distanceTo(locatable: Locatable): Int
    fun isOnScreen(): Boolean
    fun distanceTo(tile: Tile): Int
}