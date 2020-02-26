package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Model
import com.p3achb0t._runestar_interfaces.Obj
import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo
import com.p3achb0t.api.wrappers.utils.getConvexHullFromModel
import com.p3achb0t.api.wrappers.utils.getTrianglesFromModel
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
        return getGlobalLocation().getInteractPoint()
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
                position.y / 128 + ctx?.client!!.getBaseY(),
                position.plane,ctx

        )
    }


    override fun isOnScreen(): Boolean {
        return ctx?.let { Calculations.isOnscreen(it,getConvexHull().bounds ) } ?: false
    }

    suspend fun take() {
        val inventoryCount = ctx?.client?.let { ctx!!.inventory.getCount() }
        if (interact("Take")) {
        }
    }

    /**
     * added by sirscript
     * doaction (local location.x + y and id of the grounditem
     */

    suspend fun takedoAction() {
        ctx?.inventory?.addItemToTrack(this.id)
        val doActionParams = DoActionParams(this.getLocalLocation().x, this.getLocalLocation().y, 20, this.id, "", "", 0 ,0)
        ctx?.mouse?.doAction(doActionParams)
    }

    fun getTriangles(): ArrayList<Polygon> {
        var model: Model? = null
        //Go to the Tile, look at the item piles for the model.
        model = getModelFromTile()


        return if(model != null && ctx?.client != null) {
            getTrianglesFromModel(position, model, ctx!!)
        }else{
            ArrayList()
        }
    }

    private fun getModelFromTile(): Model? {
        var model: Model? = null
        val tiles = ctx?.client?.getScene()?.getTiles()
        val regional = this.getLocalLocation()

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
        return if(model != null && ctx?.client != null) {
            getConvexHullFromModel(position, model, ctx!!)
        }else{
            Polygon()
        }
    }

}
