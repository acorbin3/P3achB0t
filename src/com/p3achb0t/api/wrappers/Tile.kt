package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.Calculations.Companion.getCanvasTileAreaPoly
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon

//Tile are stored in global coordinates.

//Default of -1,-1 means the tile is not valid
class Tile(val x: Int = -1, val y: Int = -1, val z: Int = 0) : Locatable, Interactable {

    fun getPolyBounds(): Polygon {
        val regional = getRegionalLocation()
        return getCanvasTileAreaPoly(regional.x, regional.y)
    }

    override fun toString(): String {
        return "($x,$y,$z)"
    }

    override suspend fun clickOnMiniMap(): Boolean {
        val regional = getRegionalLocation()
        val point = Calculations.worldToMiniMap(regional.x, regional.y)
        return Main.mouse.click(point)
    }

    override fun getInteractPoint(): Point {
        val regional = getRegionalLocation()
        val poly = Calculations.getCanvasTileAreaPoly(regional.x, regional.y)
        return getRandomPoint(poly)
    }

    override fun isOnScreen(): Boolean {
        val tilePoly = getCanvasTileAreaPoly(getRegionalLocation().x, getRegionalLocation().y)
        return Calculations.isOnscreen(tilePoly.bounds)
    }

    override fun distanceTo(locatable: Locatable): Int {
        return Calculations.distanceBetween(getGlobalLocation(), locatable.getGlobalLocation())
    }

    override fun distanceTo(tile: Tile): Int {
        return Calculations.distanceBetween(getGlobalLocation(), tile)
    }

    // This is distance to local player
    override fun distanceTo(): Int {
        return Calculations.distanceTo(this)
    }

    override fun draw(g: Graphics2D, color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGlobalLocation(): Tile {
        return this
    }

    override fun draw(g: Graphics2D) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}