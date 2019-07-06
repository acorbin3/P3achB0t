package com.p3achb0t.api.wrappers.interfaces

import com.p3achb0t.api.Calculations
import com.p3achb0t.api.wrappers.Tile
import java.awt.Color
import java.awt.Graphics2D

interface Locatable {
    fun draw(g: Graphics2D)
    fun draw(g: Graphics2D, color: Color)
    fun getLocation(): Tile
    fun isOnScreen(): Boolean

    fun distanceTo(): Int {
        return Calculations.distanceTo(getLocation())
    }

    fun distanceTo(locatable: Locatable): Int {
        return Calculations.distanceBetween(getLocation(), locatable.getLocation())
    }

    fun distanceTo(tile: Tile): Int {
        return Calculations.distanceBetween(getLocation(), tile)
    }
}