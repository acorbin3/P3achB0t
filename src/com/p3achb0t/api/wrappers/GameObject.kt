package com.p3achb0t.api.wrappers

import com.p3achb0t.MainApplet
import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t._runestar_interfaces.Model
import com.p3achb0t._runestar_interfaces.Scenery
import com.p3achb0t._runestar_interfaces.Wall
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.ObjectPositionInfo
import com.p3achb0t.api.getConvexHullFromModel
import com.p3achb0t.api.getTrianglesFromModel
import com.p3achb0t.api.painting.getObjectComposite
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon
import java.util.*

class GameObject(
        val sceneryObject: Scenery? = null,
        val wallObject: Wall? = null,
        client: com.p3achb0t._runestar_interfaces.Client,
        override var loc_client: Client? = client,
        mouse: Mouse
) : Locatable,
    Interactable(client, mouse) {
    val id: Int
        get() {
            return when {
                sceneryObject != null -> sceneryObject.getTag().shr(17).and(0x7fff).toInt()
                wallObject != null -> wallObject.getTag().shr(17).and(0x7fff).toInt()
                else -> 0
            }
        }
    val name: String
        get() {
            val sceneData = client.getLocType_cached()
            val objectComposite = getObjectComposite(sceneData, id)
            return objectComposite?.getName().toString()
        }
    private val objectPositionInfo: ObjectPositionInfo
        get() {
            return when {
                sceneryObject != null -> ObjectPositionInfo(
                    sceneryObject.getCenterX(),
                    sceneryObject.getCenterY(),
                    sceneryObject.getOrientation()
                )
                wallObject != null -> ObjectPositionInfo(
                    wallObject.getX(),
                    wallObject.getY(),
                    wallObject.getOrientationA()
                )
                else -> ObjectPositionInfo(0, 0, 0)
            }
        }
    val model: Model?
        get() {
            return when {
                sceneryObject != null -> sceneryObject.getEntity() as Model
                wallObject != null -> wallObject.getEntity1() as Model
                else -> null
            }
        }
    override fun isMouseOverObj(): Boolean {
        val mousePoint = Point(mouse.mouseEvent?.x ?: -1,mouse.mouseEvent?.y ?: -1)
        return getConvexHull().contains(mousePoint)
    }
    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return  Calculations.worldToScreen(region.x, region.y, sceneryObject?.getHeight() ?: 0, client)
    }
    override suspend fun clickOnMiniMap(): Boolean {
        return when {
            sceneryObject != null -> mouse.click(
                    Calculations.worldToMiniMap(
                            sceneryObject.getCenterX(),
                            sceneryObject.getCenterY(),
                            client
                    )
            )
            wallObject != null -> mouse.click(
                    Calculations.worldToMiniMap(
                            wallObject.getX(),
                            wallObject.getY(),
                            client
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
        if (client != null) {
            return when {
                sceneryObject != null -> Tile(
                        sceneryObject.getCenterX() / 128 + client.getBaseX(),
                        sceneryObject.getCenterY() / 128 + client.getBaseY(),
                        sceneryObject.getPlane()
                )
                wallObject != null -> Tile(
                        wallObject.getX() / 128 + client.getBaseX(),
                        wallObject.getY() / 128 + client.getBaseY(),
                        sceneryObject?.getPlane() ?: 0
                )
                else -> Tile(-1, -1)
            }
        }else{
            return Tile()
        }
    }


    override fun isOnScreen(): Boolean {
        return client?.let { Calculations.isOnscreen(it,getConvexHull().bounds ) } ?: false
    }

    fun getTriangles(): ArrayList<Polygon> {

        return if (model != null) {
            val positionInfo =
                objectPositionInfo

            val modelTriangles =
                getTrianglesFromModel(positionInfo, model!!,client!! )

            modelTriangles
        } else {
            ArrayList()
        }
    }


    fun getConvexHull(): Polygon {
        val positionInfo = objectPositionInfo
        return when {
            sceneryObject != null -> getConvexHullFromModel(positionInfo, sceneryObject.getEntity() as Model,client!! )
            wallObject != null -> getConvexHullFromModel(positionInfo, wallObject.getEntity1() as Model,client!! )
            else -> Polygon()
        }
    }

}