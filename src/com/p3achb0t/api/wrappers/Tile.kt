package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Calculations
import java.awt.Color
import java.awt.Graphics2D

//TODO extend interactable in the future
class Tile(val x: Int, val y: Int, val z: Int = 0) : Locatable {
    override fun isOnScreen(): Boolean {
        val tilePoly = Calculations.getCanvasTileAreaPoly(x, y)
        return Calculations.isOnscreen(tilePoly.bounds)
    }

    override fun distanceTo(locatable: Locatable): Int {
        return Calculations.distanceBetween(getLocation(), locatable.getLocation())
    }

    override fun distanceTo(tile: Tile): Int {
        return Calculations.distanceBetween(getLocation(), tile)
    }

    override fun distanceTo(): Int {
        return Calculations.distanceTo(this)
    }

    override fun draw(g: Graphics2D, color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocation(): Tile {
        return this
    }

    override fun draw(g: Graphics2D) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}