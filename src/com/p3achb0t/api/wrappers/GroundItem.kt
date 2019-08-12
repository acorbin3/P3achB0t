package com.p3achb0t.api.wrappers

import com.p3achb0t.MainApplet
import com.p3achb0t._runestar_interfaces.EvictingDualNodeHashTable
import com.p3achb0t._runestar_interfaces.Model
import com.p3achb0t.api.*
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import com.p3achb0t.api.wrappers.tabs.Inventory
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon
import java.util.*

class GroundItem(val id: Int, val position: ObjectPositionInfo, val stackSize: Int = 0) : Locatable,
    Interactable {
    override suspend fun clickOnMiniMap(): Boolean {
        return MainApplet.mouse.click(Calculations.worldToMiniMap(position.x, position.y))
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
            position.x / 128 + Client.client.getBaseX(),
            position.y / 128 + Client.client.getBaseY(),
            position.plane
        )
    }


    override fun isOnScreen(): Boolean {
        return Calculations.isOnscreen(getConvexHull().bounds)
    }

    suspend fun take() {
        val inventoryCount = Inventory.getCount()
        if (interact("Take")) {
            Utils.waitFor(2, object : Utils.Condition {

                override suspend fun accept(): Boolean {
                    delay(100)
                    println("Waiting for inventory to change $inventoryCount == ${Inventory.getCount()}")
                    return inventoryCount != Inventory.getCount()
                }
            })
        }
    }

    fun getTriangles(): ArrayList<Polygon> {
        val groundItemModels = Client.client.getObjType_cachedModels()
        val model: Model? = getModel(groundItemModels)
        return model?.let { getTrianglesFromModel(position, it) } ?: ArrayList()
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
        val groundItemModels = Client.client.getObjType_cachedModels()
        val model: Model? = getModel(groundItemModels)
        return model?.let { getConvexHullFromModel(position, it) } ?: Polygon()
    }

}