package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.wrappers.interfaces.Locatable
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

    override fun getGlobalLocation(): Tile {
        return Tile(
            raw.getLocalX() / 128 + Main.clientData.getBaseX(),
            raw.getLocalY() / 128 + Main.clientData.getBaseY(),
            Main.clientData.getPlane()
        )
    }

}