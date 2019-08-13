package com.p3achb0t.api.wrappers

import com.p3achb0t.MainApplet
import com.p3achb0t._runestar_interfaces.BoundaryObject
import com.p3achb0t._runestar_interfaces.GameObject
import com.p3achb0t._runestar_interfaces.Model
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.ObjectPositionInfo
import com.p3achb0t.api.getConvexHullFromModel
import com.p3achb0t.api.getTrianglesFromModel
import com.p3achb0t.api.painting.getObjectComposite
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon
import java.util.*

class GameObject(val gameObject: GameObject? = null, val boundaryObject: BoundaryObject? = null) : Locatable,
    Interactable {
    val id: Int
        get() {
            return when {
                gameObject != null -> gameObject.getTag().shr(17).and(0x7fff).toInt()
                boundaryObject != null -> boundaryObject.getTag().shr(17).and(0x7fff).toInt()
                else -> 0
            }
        }
    val name: String
        get() {
            val sceneData = Client.client.getLocType_cached()
            val objectComposite =
                getObjectComposite(sceneData, id)
            return objectComposite?.getName().toString()
        }
    private val objectPositionInfo: ObjectPositionInfo
        get() {
            return when {
                gameObject != null -> ObjectPositionInfo(
                    gameObject.getCenterX(),
                    gameObject.getCenterY(),
                    gameObject.getOrientation()
                )
                boundaryObject != null -> ObjectPositionInfo(
                    boundaryObject.getX(),
                    boundaryObject.getY(),
                    boundaryObject.getOrientationA()
                )
                else -> ObjectPositionInfo(0, 0, 0)
            }
        }
    val model: Model?
        get() {
            return when {
                gameObject != null -> gameObject.getEntity() as Model
                boundaryObject != null -> boundaryObject.getEntity1() as Model
                else -> null
            }
        }

    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return Calculations.worldToScreen(region.x, region.y, gameObject?.getHeight() ?: 0)
    }
    override suspend fun clickOnMiniMap(): Boolean {
        return when {
            gameObject != null -> MainApplet.mouse.click(
                Calculations.worldToMiniMap(
                    gameObject.getCenterX(),
                    gameObject.getCenterY()
                )
            )
            boundaryObject != null -> MainApplet.mouse.click(
                Calculations.worldToMiniMap(
                    boundaryObject.getX(),
                    boundaryObject.getY()
                )
            )
            else -> false
        }
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
        return when {
            gameObject != null -> Tile(
                gameObject.getCenterX() / 128 + Client.client.getBaseX(),
                gameObject.getCenterY() / 128 + Client.client.getBaseY(),
                gameObject.getPlane()
            )
            boundaryObject != null -> Tile(
                boundaryObject.getX() / 128 + Client.client.getBaseX(),
                boundaryObject.getY() / 128 + Client.client.getBaseY(),
                gameObject?.getPlane() ?: 0
            )
            else -> Tile(-1, -1)
        }
    }


    override fun isOnScreen(): Boolean {
        return Calculations.isOnscreen(getConvexHull().bounds)
    }

    fun getTriangles(): ArrayList<Polygon> {

        return if (model != null) {
            val positionInfo =
                objectPositionInfo

            val modelTriangles =
                getTrianglesFromModel(positionInfo, model!!)

            modelTriangles
        } else {
            ArrayList()
        }
    }


    fun getConvexHull(): Polygon {
        val positionInfo = objectPositionInfo
        return when {
            gameObject != null -> getConvexHullFromModel(positionInfo, gameObject.getEntity() as Model)
            boundaryObject != null -> getConvexHullFromModel(positionInfo, boundaryObject.getEntity1() as Model)
            else -> Polygon()
        }
    }

}