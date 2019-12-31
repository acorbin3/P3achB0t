package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.EvictingDualNodeHashTable
import com.p3achb0t._runestar_interfaces.Model
import com.p3achb0t.api.*
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon
import java.util.*

class GroundItem(
        ctx: Context,
        val id: Int,
        val position: ObjectPositionInfo,
        val stackSize: Int = 0,
        override var loc_ctx: Context? = ctx
) : Interactable(ctx),
    Locatable {
    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return ctx.let { ctx?.client?.getPlane()?.let { it1 -> it?.let { it2 -> Calculations.worldToScreen(region.x, region.y, it1, it2) } } } ?: Point(0,0)
    }
    override fun isMouseOverObj(): Boolean {
        val mousePoint = Point(ctx?.mouse?.getX() ?: -1, ctx?.mouse?.getY() ?: -1)
        return getConvexHull().contains(mousePoint)
    }
    override suspend fun clickOnMiniMap(): Boolean {
        return ctx.let { it?.let { it1 -> Calculations.worldToMiniMap(position.x, position.y, it1) } }.let { it?.let { it1 -> ctx?.mouse?.click(it1) } } ?: false
    }

    override fun getInteractPoint(): Point {
        return getRandomPoint(getConvexHull())
    }

    override fun draw(g: Graphics2D) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics2D, color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGlobalLocation(): Tile {
        return Tile(
                position.x / 128 + ctx?.client?.getBaseX()!!,
                position.y / 128 + ctx?.client.getBaseY(),
                position.plane,ctx

        )
    }


    override fun isOnScreen(): Boolean {
        return ctx?.let { Calculations.isOnscreen(it,getConvexHull().bounds ) } ?: false
    }

    suspend fun take() {
        val inventoryCount = ctx?.client?.let { ctx.inventory.getCount() }
        if (interact("Take")) {
            Utils.waitFor(2, object : Utils.Condition {

                override suspend fun accept(): Boolean {
                    delay(100)
                    println("Waiting for inventory to change $inventoryCount == ${ctx?.client?.let { ctx.inventory.getCount() }}")
                    return inventoryCount != ctx?.client?.let { ctx.inventory.getCount() }
                }
            })
        }
    }

    fun getTriangles(): ArrayList<Polygon> {
        val groundItemModels = ctx?.client?.getObjType_cachedModels()
        val model: Model? = groundItemModels?.let { getModel(it) }
        return if(model != null && ctx?.client != null) {
            getTrianglesFromModel(position, model, ctx)
        }else{
            ArrayList()
        }
    }

    private fun getModel(
        groundItemModels: EvictingDualNodeHashTable
    ): Model? {
        var model1: Model? = null
        groundItemModels.getHashTable().getBuckets().iterator().forEach {
            if (it != null) {
                var next = it.getNext()
                while (next.getKey() > 0 && next is Model) {
                    try {
                        if (next.getKey().toInt() == this.id) {
                            model1 = next
                            break
                        }
                        next = next.getNext()
                    } catch (e: Exception) {
                        println(e.stackTrace)
                    }
                }
            }
        }
        return model1
    }

    fun getConvexHull(): Polygon {
        val groundItemModels = ctx?.client!!.getObjType_cachedModels()
        val model: Model? = getModel(groundItemModels)
        return if(model != null && ctx?.client != null) {
            getConvexHullFromModel(position, model,ctx )
        }else{
            Polygon()
        }
    }

}