package com.p3achb0t.api.wrappers

import com.p3achb0t.MainApplet
import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.Calculations.Companion.getCanvasTileAreaPoly
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import java.applet.Applet
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon

//Tile are stored in global coordinates.

//Default of -1,-1 means the tile is not valid
class Tile(
        val x: Int = -1,
        val y: Int = -1,
        val z: Int = 0,
        client: Client?=null,
        mouse: Mouse?=null,
        override var loc_client: Client? = client,
        override var loc_keyboard: Keyboard? = null
) : Locatable, Interactable(client, mouse) {
    companion object {
        val NIL = Tile(-1, -1, -1)
    }

    fun getPolyBounds(client: com.p3achb0t._runestar_interfaces.Client): Polygon {
        val regional = getRegionalLocation()
        return getCanvasTileAreaPoly(client, regional.x, regional.y)
    }
    override fun isMouseOverObj(): Boolean {
        val mousePoint = Point(mouse?.mouseEvent?.x ?: -1,mouse?.mouseEvent?.y ?: -1)
        return client?.let { getCanvasTileAreaPoly(it, getRegionalLocation().x, getRegionalLocation().y).contains(mousePoint) } ?: false
    }

    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return client?.let { Calculations.worldToScreen(region.x, region.y, z, it) } ?: Point()
    }
    override fun toString(): String {
        return "($x,$y,$z)"
    }

    override suspend fun clickOnMiniMap(): Boolean {
        val regional = getRegionalLocation()
        val point = client.let { it?.let { it1 -> Calculations.worldToMiniMap(regional.x, regional.y, it1) } }
        return point?.let { mouse?.click(it) } ?: false
    }

    override fun getInteractPoint(): Point {
        val regional = getRegionalLocation()
        val poly = client?.let { getCanvasTileAreaPoly(it, regional.x, regional.y) }
        return poly?.let { getRandomPoint(it) } ?: Point()
    }

    override fun isOnScreen(): Boolean {
        val tilePoly = client?.let { getCanvasTileAreaPoly(it, getRegionalLocation().x, getRegionalLocation().y) }
        return client?.let { tilePoly?.bounds?.let { it1 -> Calculations.isOnscreen(it, it1) } } ?: false
    }

    override fun distanceTo(locatable: Locatable): Int {
        return Calculations.distanceBetween(getGlobalLocation(), locatable.getGlobalLocation())
    }

    override fun distanceTo(tile: Tile): Int {
        return Calculations.distanceBetween(getGlobalLocation(), tile)
    }

    // This is distance to local player
    override fun distanceTo(): Int {
        return client?.let { Calculations.distanceTo(this, it) } ?: -1
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