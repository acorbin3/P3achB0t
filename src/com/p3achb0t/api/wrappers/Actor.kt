package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import java.awt.Color
import java.awt.Graphics2D


open class Actor(var raw: com.p3achb0t.hook_interfaces.Actor) : Locatable {
    override fun isOnScreen(): Boolean {
        val tilePoly = Calculations.getCanvasTileAreaPoly(
            raw.getLocalX(),
            raw.getLocalY()
        )
        return Calculations.isOnscreen(tilePoly.bounds)
    }


    override fun draw(g: Graphics2D) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics2D, color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocation(): Tile {

        return Tile(raw.getLocalX(), raw.getLocalY(), Main.clientData.getPlane())
    }

    override fun distanceTo(): Int {
        return Calculations.distanceTo(getLocation())
    }

    override fun distanceTo(locatable: Locatable): Int {
        return Calculations.distanceBetween(getLocation(), locatable.getLocation())
    }

    override fun distanceTo(tile: Tile): Int {
        return Calculations.distanceBetween(getLocation(), tile)
    }
}