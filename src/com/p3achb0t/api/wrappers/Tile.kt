package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.Calculations.Companion.LOCAL_HALF_TILE_SIZE
import com.p3achb0t.api.wrappers.utils.Calculations.Companion.getCanvasTileAreaPoly
import com.p3achb0t.api.wrappers.utils.CollisionFlag.BLOCKED
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon

// Tile are stored in global coordinates.
// There is a context associated with the tile so we can make it Intractable. Problem is it can be annoying to want to specify
// a context for a path or list of Tiles. Thus we have updated the ctx to be updateable

//Default of -1,-1 means the tile is not valid
class Tile(
    var x: Int = -1,
    var y: Int = -1,
    val z: Int = 0,
    ctx: Context? = null,
    override var loc_ctx: Context? = ctx,
    // This flag is intended for random path walking. some tiles you dont want to randomize due to
    // tight corridors
    var randomize: Boolean = false
) : Locatable, Interactable(ctx) {

    fun isTileWalkAble(): Boolean {
        val collisionMap = ctx?.client?.getCollisionMaps()
        var local = this.getLocalLocation()
        if (collisionMap != null) {
            if (local.x in 0..103 && local.y in 0..104) {
                val collisionFlag = collisionMap[ctx?.client?.getPlane() ?: 0].getFlags()[local.x][local.y]
                return collisionFlag and BLOCKED == 0
            }
        }

        return false
    }

    val plane get() = z

    companion object {
        val NIL = Tile(-1, -1, -1, null)
    }

    fun updateCTX(ctx: Context) {
        this.ctx = ctx
        this.loc_ctx = ctx
    }

    fun getPolyBounds(size: Int = 1): Polygon {
        val region = getRegionalLocation()
        return this.ctx?.let { getCanvasTileAreaPoly(it, region.x, region.y, size = size) } ?: Polygon()
    }

    override fun isMouseOverObj(): Boolean {
        val mousePoint = Point(ctx?.mouse?.getX() ?: -1, ctx?.mouse?.getY() ?: -1)
        return ctx?.client?.let {
            getCanvasTileAreaPoly(
                ctx!!,
                getRegionalLocation().x,
                getRegionalLocation().y
            ).contains(mousePoint)
        }
            ?: false
    }

    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return ctx?.client?.let { Calculations.worldToScreen(region.x, region.y, z, ctx!!) } ?: Point()
    }

    override fun toString(): String {
        return "($x,$y,$z)"
    }

    override suspend fun clickOnMiniMap(): Boolean {
        if (ctx == null) {
            logger.error("ERROR: ctx is null, click on minimap cant be completed. Please provide the ctx")
            return false
        }
        val regional = getRegionalLocation()
//        println("Clicking ${regional.x} ${regional.y}")
        val point = Calculations.worldToMiniMap(regional.x, regional.y, ctx!!)
        return ctx!!.mouse.click(point)
    }

    suspend fun clickOnMiniMap(x: Int, y: Int): Boolean {
        // translation
        if (ctx == null) {
            logger.error("ERROR: ctx is null, click on minimap cant be completed. Please provide the ctx")
            return false
        }
        val regional = getRegionalLocation()
        val point = Calculations.worldToMiniMap(regional.x + x, regional.y + y, ctx!!)
        return ctx!!.mouse.click(point)
    }

    override fun getInteractPoint(): Point {
        if (ctx == null) {
            logger.error("ERROR: ctx is null, interaction point cant be computed. Please provide the ctx")
            return Point(-1, -1)
        }
        val regional = getRegionalLocation()
        val poly = getCanvasTileAreaPoly(ctx!!, regional.x, regional.y)
        return getRandomPoint(poly)
    }

    override fun isOnScreen(): Boolean {
        return if (ctx == null) {
            logger.error("ERROR: ctx is null, isOnScreen cant be computed. Please provide the ctx")
            false
        } else {
            val tilePoly = getCanvasTileAreaPoly(ctx!!, getRegionalLocation().x, getRegionalLocation().y)
            Calculations.isOnscreen(ctx!!, tilePoly.bounds)
        }

    }

    override fun distanceTo(locatable: Locatable): Int {
        return Calculations.distanceBetween(getGlobalLocation(), locatable.getGlobalLocation())
    }

    override fun distanceTo(tile: Tile): Int {
        return Calculations.distanceBetween(getGlobalLocation(), tile)
    }

    fun isSameTile(tile:Tile): Boolean{
        return x == tile.x && y == tile.y && z == tile.z
    }

    // This is distance to local player
    override fun distanceTo(): Int {
        if (ctx == null) {
            logger.error("ERROR: ctx is null, for tile $this,  distance to player cant be computed. Please provide the ctx")
            for (stack in Thread.currentThread().stackTrace) {
                println(stack)
            }
        }
        return ctx?.let { Calculations.distanceTo(this, it) } ?: -1
    }

    override fun draw(g: Graphics2D, color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGlobalLocation(): Tile {
        return this
    }

    override fun getRegionalLocation(): Tile {
        //For some reason the defined tiles are shifted by half a tile. This adding half to the x and y aligns the tiles to the gird
        var rTile = super.getRegionalLocation()
        rTile.x = rTile.x + LOCAL_HALF_TILE_SIZE
        rTile.y = rTile.y + LOCAL_HALF_TILE_SIZE
        return rTile
    }

    override fun draw(g: Graphics2D) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(other: Any?): Boolean {
        val tile = other as Tile
        return this.x == tile.x && this.y == tile.y
    }

    fun north(): Tile {
        return Tile(x, y + 1, z, ctx = ctx)
    }

    fun south(): Tile {
        return Tile(x, y - 1, z, ctx = ctx)
    }

    fun east(): Tile {
        return Tile(x + 1, y, z, ctx = ctx)
    }

    fun west(): Tile {
        return Tile(x - 1, y, z, ctx = ctx)
    }

    fun southWest(): Tile {
        return Tile(x - 1, y - 1, z, ctx = ctx)
    }

    fun northWest(): Tile {
        return Tile(x - 1, y + 1, z, ctx = ctx)
    }

    fun southEast(): Tile {
        return Tile(x + 1, y - 1, z, ctx = ctx)
    }

    fun northEast(): Tile {
        return Tile(x + 1, y + 1, z, ctx = ctx)
    }

}