package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.ObjectPositionInfo
import com.p3achb0t.api.getConvexHullFromModel
import com.p3achb0t.api.getTrianglesFromModel
import com.p3achb0t.api.painting.getObjectComposite
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import com.p3achb0t.hook_interfaces.BoundaryObject
import com.p3achb0t.hook_interfaces.GameObject
import com.p3achb0t.hook_interfaces.Model
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
                gameObject != null -> gameObject.getId().shr(17).and(0x7fff).toInt()
                boundaryObject != null -> boundaryObject.getId().shr(17).and(0x7fff).toInt()
                else -> 0
            }
        }
    val name: String
        get() {
            val sceneData = Main.clientData.getObjectCompositeCache()
            val objectComposite =
                getObjectComposite(sceneData, id)
            return objectComposite?.getName().toString()
        }
    private val objectPositionInfo: ObjectPositionInfo
        get() {
            return when {
                gameObject != null -> ObjectPositionInfo(
                    gameObject.getX(),
                    gameObject.getY(),
                    gameObject.getOrientation()
                )
                boundaryObject != null -> ObjectPositionInfo(
                    boundaryObject.getX(),
                    boundaryObject.getY(),
                    boundaryObject.getOrientation()
                )
                else -> ObjectPositionInfo(0, 0, 0)
            }
        }
    val model: Model?
        get() {
            return when {
                gameObject != null -> gameObject.getRenderable() as Model
                boundaryObject != null -> boundaryObject.getRenderable() as Model
                else -> null
            }
        }

    override suspend fun clickOnMiniMap(): Boolean {
        return when {
            gameObject != null -> Main.mouse.click(Calculations.worldToMiniMap(gameObject.getX(), gameObject.getY()))
            boundaryObject != null -> Main.mouse.click(
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
                gameObject.getX() / 128 + Main.clientData.getBaseX(),
                gameObject.getY() / 128 + Main.clientData.getBaseY(),
                gameObject.getPlane()
            )
            boundaryObject != null -> Tile(
                boundaryObject.getX() / 128 + Main.clientData.getBaseX(),
                boundaryObject.getY() / 128 + Main.clientData.getBaseY(),
                boundaryObject.getPlane()
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
            gameObject != null -> getConvexHullFromModel(positionInfo, gameObject.getRenderable() as Model)
            boundaryObject != null -> getConvexHullFromModel(positionInfo, boundaryObject.getRenderable() as Model)
            else -> Polygon()
        }
    }

}