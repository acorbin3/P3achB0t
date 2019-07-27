package com.p3achb0t.api.painting

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.ObjectPositionInfo
import com.p3achb0t.api.getConvexHullFromModel
import com.p3achb0t.api.getTrianglesFromModel
import com.p3achb0t.api.wrappers.GameObject
import com.p3achb0t.hook_interfaces.Cache
import com.p3achb0t.hook_interfaces.Model
import com.p3achb0t.hook_interfaces.ObjectComposite
import java.awt.Color
import java.awt.Graphics

fun gameObjectPaint(g: Graphics) {
    if (true) {
        val sceneData = Main.clientData.getObjectCompositeCache()
        val region = Main.clientData.getRegion()

        region.getTiles().iterator().forEach { plane ->
            plane.iterator().forEach { row ->
                row.iterator().forEach { tile ->
                    if (tile != null) {
                        if (tile.getObjects().isNotEmpty()) {
                            var count = 0
                            tile.getObjects().iterator().forEach {
                                if (it != null && it.getId() > 0) {
                                    count += 1
                                    // Print out the polygons for the models
                                    if (false) {
                                        val tilePolygon =
                                            Calculations.getCanvasTileAreaPoly(
                                                it.getX(),
                                                it.getY()
                                            )
                                        g.color = Color.ORANGE
                                        g.drawPolygon(tilePolygon)
                                    }
                                    val point =
                                        Calculations.worldToScreen(
                                            it.getX(),
                                            it.getY(),
                                            tile.getPlane()
                                        )
                                    if (point.x != -1 && point.y != -1 && Calculations.isOnscreen(
                                            point
                                        )
                                    ) {
                                        g.color = Color.GREEN
                                        val id = it.getId().shr(17).and(0x7fff).toInt()
                                        val rawID = it.getId().shr(14).and(0x7fff)
                                        //                                            println("${it.getWidgetID()},$rawID,$widgetID,${it.getRenderable().getWidgetID()}")
                                        val objectComposite =
                                            getObjectComposite(sceneData, id)
                                        val point2 =
                                            Calculations.worldToScreen(
                                                it.getX(),
                                                it.getY(),
                                                it.getRenderable().getModelHeight()
                                            )
                                        val go = GameObject(it)
                                        val globalPos = go.getGlobalLocation()

                                        g.drawString(
                                            objectComposite?.getName() + "($id)(${globalPos.x},${globalPos.y}",
                                            point2.x,
                                            point2.y
                                        )
                                    }

                                    //Printing out the model and the hull
                                    if (false) {
                                        val model = it.getRenderable()
                                        if (model is Model) {
                                            val positionInfo =
                                                ObjectPositionInfo(
                                                    it.getX(),
                                                    it.getY(),
                                                    it.getOrientation()
                                                )

                                            val modelTriangles =
                                                getTrianglesFromModel(
                                                    positionInfo,
                                                    model
                                                )
                                            g.color = Color.RED
                                            modelTriangles.forEach {
                                                g.drawPolygon(it)
                                            }
                                            val hull = getConvexHullFromModel(
                                                positionInfo,
                                                model
                                            )
                                            g.color = Color.CYAN
                                            g.drawPolygon(hull)

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getObjectComposite(
    objectCache: Cache,
    gameObjectId: Int
): ObjectComposite? {
    var desiredGameObject1: ObjectComposite? = null
    objectCache.getHashTable().getBuckets().iterator().forEach { bucketItem ->
        if (bucketItem != null) {

            var objectComposite = bucketItem.getNext()
            while (objectComposite != null
                && objectComposite is ObjectComposite
                && objectComposite != bucketItem
            ) {
                if (objectComposite.getId() > 0
                    && objectComposite.getId().toInt() == gameObjectId
                ) {
                    desiredGameObject1 = objectComposite
                    break
                }
                objectComposite = objectComposite.getNext()
            }
        }
    }
    return desiredGameObject1
}