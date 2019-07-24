package com.p3achb0t.api.wrappers.interfaces

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.Constants
import com.p3achb0t.api.wrappers.Tile
import java.awt.Color
import java.awt.Graphics2D

interface Locatable {
    fun draw(g: Graphics2D)
    fun draw(g: Graphics2D, color: Color)


    // In general players, objects, ground items getting x and y are stored in Regional coordinates.
    // For consistancy internal getting locations will be done using global coordnates. To convert Regional to global,
    // you right shift, and then add the client.baseX and client.baseY

    fun getGlobalLocation(): Tile

    // This function will remove the base and shift over by 7
    fun getLocalLocation(): Tile {
        val tile = getGlobalLocation()
        val x = (tile.x - Main.clientData.getBaseX())
        val y = (tile.y - Main.clientData.getBaseY())

        return Tile(x, y, tile.z)
    }

    fun getRegionalLocation(): Tile {
        val tile = getGlobalLocation()
        val x = (tile.x - Main.clientData.getBaseX() shl Constants.REGION_SHIFT)
        val y = (tile.y - Main.clientData.getBaseY() shl Constants.REGION_SHIFT)

        return Tile(x, y, tile.z)
    }
    fun isOnScreen(): Boolean

    fun distanceTo(): Int {
        return Calculations.distanceTo(getGlobalLocation())
    }

    fun distanceTo(locatable: Locatable): Int {
        return Calculations.distanceBetween(getGlobalLocation(), locatable.getGlobalLocation())
    }

    fun distanceTo(tile: Tile): Int {
        return Calculations.distanceBetween(getGlobalLocation(), tile.getGlobalLocation())
    }
}