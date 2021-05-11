package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.interfaces.Model
import com.p3achb0t.api.interfaces.Obj
import com.p3achb0t.api.interfaces.Projectile
import com.p3achb0t.api.wrappers.interfaces.ActorTargeting
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo
import com.p3achb0t.api.wrappers.utils.getConvexHullFromModel
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon
import kotlin.math.min

class Projectile(
        ctx: Context,
        val raw: Projectile,
        override var loc_ctx: Context? = ctx,
        override var actTar_ctx: Context? = ctx
) : Interactable(ctx),
        Locatable, ActorTargeting {

    val id get() = raw.getId()
    val position: ObjectPositionInfo
        get() {
            val tileHeight = Calculations.getTileHeight(ctx!!, raw.getPlane(), raw.getX().toInt(), raw.getY().toInt())

            val height = tileHeight - raw.getZ().toInt()
            //println("TileHeight (${raw.getX()},${raw.getY()}) $tileHeight. z=${raw.getZ()}. height = $height")
            return ObjectPositionInfo(raw.getX().toInt(),
                    raw.getY().toInt(),
                    orientation = raw.getYaw() % 2048,
                    plane = raw.getPlane(),
                    z = raw.getZ().toInt(),
                    tileHeight = tileHeight)
        }
    val speed get() = raw.getSpeed()
    val speedX get() = raw.getSpeedX()
    val speedY get() = raw.getSpeedY()
    override val npcTargetIndex: Int
        get() = raw.getTargetIndex().let { if (it > 0) it - 1 else -1 }
    val acceleration get() = raw.getAccelerationZ()
    val x get() = raw.getX()
    val y get() = raw.getY()
    val z get() = raw.getZ()

    val getPosition = Tile(raw.getX().toInt() / 128 + ctx.client.getBaseX(), raw.getY().toInt() / 128 + ctx.client.getBaseY(), ctx=ctx)
    val sourcePosition = Tile(raw.getSourceX() / 128 + ctx.client.getBaseX(), raw.getSourceY() / 128 + ctx.client.getBaseY(), ctx=ctx)
    //  Subtracting 6 cycles seems to make the predicted  more stable, otherwise the tile jumps around and is off by 1 often
    val remaining = min( (raw.getCycleEnd() - ctx.client.getCycle() - 5), raw.getCycleEnd() - raw.getCycleStart())
    val pX = (raw.getX() + (raw.getSpeedX() * remaining)).toInt()
    val pY = (raw.getY() + (raw.getSpeedY() * remaining)).toInt();

    // pX and pY are bit shifted by 7 so we will divide by 128 to get the local position and then add the client base to get back the global position
    // Note: the predictedTile seems to be not predicted correctly until the projectile leaves its original source. So consider only looking
    // at this once the sourcePosition is not the same as the getPosition
    val predictedTile = Tile(pX / 128 + ctx.client.getBaseX(), pY / 128 + ctx.client.getBaseY(), ctx = ctx)


    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return ctx.let { ctx?.client?.getPlane()?.let { it1 -> it?.let { it2 -> Calculations.worldToScreen(region.x, region.y, it1, it2) } } }
                ?: Point(0, 0)
    }

    override fun isMouseOverObj(): Boolean {
        val mousePoint = Point(ctx?.mouse?.getX() ?: -1, ctx?.mouse?.getY() ?: -1)
        return getConvexHull().contains(mousePoint)
    }

    override suspend fun clickOnMiniMap(): Boolean {
        return ctx.let { it?.let { it1 -> Calculations.worldToMiniMap(position.x, position.y, it1) } }.let { it?.let { it1 -> ctx?.mouse?.click(it1) } }
                ?: false
    }


    override fun draw(g: Graphics2D) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics2D, color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun isOnScreen(): Boolean {
        return ctx?.let { Calculations.isOnscreen(it, getConvexHull().bounds) } ?: false
    }


    override fun getGlobalLocation(): Tile {
        return Tile(
                x.toInt() / 128 + ctx?.client?.getBaseX()!!,
                y.toInt() / 128 + ctx?.client!!.getBaseY(),
                z.toInt()

        )
    }

    override fun getInteractPoint(): Point {
        return getGlobalLocation().getInteractPoint()
    }

    private fun getModelFromTile(): Model? {
        var model: Model? = null
        val tiles = ctx?.client?.getScene()?.getTiles()
        val regional = this.getLocalLocation()
        println("${regional.z},${regional.x},${regional.y}")

        val observableTile = tiles?.get(regional.z)?.get(regional.x)?.get(regional.y)

        if (observableTile != null) {
            val objectStackPile = observableTile.getObjStack()
            if (objectStackPile != null) {
                val firstItem = objectStackPile.getFirst()
                if (firstItem != null && firstItem is Obj && firstItem.getId() == this.id) {
                    model = firstItem.getModel()
//                    println("\tFirst: ID: ${firstItem.getKey()} (${regional.z},${regional.x},${regional.y}) Found Item: ${firstItem.getId()} stackSize: ${firstItem.getQuantity()}")
                }
                val secondItem = objectStackPile.getSecond()
                if (secondItem != null && secondItem is Obj && secondItem.getId() == this.id) {
                    model = secondItem.getModel()
//                    println("\tSecond: ID: ${secondItem.getKey()} (${regional.z},${regional.x},${regional.y}) Found Item: ${secondItem.getId()} stackSize: ${secondItem.getQuantity()}")
                }
                val thirdItem = objectStackPile.getThird()
                if (thirdItem != null && thirdItem is Obj && thirdItem.getId() == this.id) {
                    model = thirdItem.getModel()
//                    println("\tThird: ID: ${thirdItem.getKey()} (${regional.z},${regional.x},${regional.y}) Found Item: ${thirdItem.getId()} stackSize: ${thirdItem.getQuantity()}")
                }
            }
        }
        return model
    }

    fun getConvexHull(): Polygon {
        val model: Model? = this.getModelFromTile()
        return if (model != null && ctx?.client != null) {
            getConvexHullFromModel(position, model, ctx!!)
        } else {
            Polygon()
        }
    }

}