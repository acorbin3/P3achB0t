package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.ObjectPositionInfo
import com.p3achb0t.api.getConvexHullFromModel
import com.p3achb0t.api.getTrianglesFromModel
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import com.p3achb0t.hook_interfaces.GameObject
import com.p3achb0t.hook_interfaces.Model
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon
import java.util.*

class GameObject(val raw: GameObject) : Locatable,
    Interactable {

    private val objectPositionInfo: ObjectPositionInfo
        get() {
            return ObjectPositionInfo(
                raw.getX(),
                raw.getY(),
                raw.getOrientation()
            )
        }

    override suspend fun clickOnMiniMap(): Boolean {
        return Main.mouse.click(Calculations.worldToMiniMap(raw.getX(), raw.getY()))
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
            raw.getX() / 128 + Main.clientData.getBaseX(),
            raw.getY() / 128 + Main.clientData.getBaseY(),
            raw.getPlane()
        )
    }


    override fun isOnScreen(): Boolean {
        return Calculations.isOnscreen(getConvexHull().bounds)
    }


    fun getTriangles(): ArrayList<Polygon> {

        val model = raw.getRenderable() as Model
        val positionInfo =
            objectPositionInfo

        val modelTriangles =
            getTrianglesFromModel(positionInfo, model)

        return modelTriangles
    }


    fun getConvexHull(): Polygon {
        val positionInfo = objectPositionInfo
        return getConvexHullFromModel(positionInfo, raw.getRenderable() as Model)
    }

}