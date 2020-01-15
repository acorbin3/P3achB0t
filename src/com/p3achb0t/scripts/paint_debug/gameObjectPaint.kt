package com.p3achb0t.scripts.paint_debug

import com.p3achb0t._runestar_interfaces.EvictingDualNodeHashTable
import com.p3achb0t._runestar_interfaces.LocType
import com.p3achb0t._runestar_interfaces.Model
import com.p3achb0t.api.*
import com.p3achb0t.api.wrappers.GameObject
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo
import com.p3achb0t.api.wrappers.utils.getConvexHullFromModel
import com.p3achb0t.api.wrappers.utils.getTrianglesFromModel
import java.awt.Color
import java.awt.Graphics

fun gameObjectPaint(g: Graphics, ctx: Context) {
    if (true) {
        val sceneData = ctx.client.getObjType_cachedModels()
        val region = ctx.client.getScene()
        val localPlayer = ctx.players.getLocal()
        var planeInt = 0
        region.getTiles().iterator().forEach { plane ->
            if (planeInt == ctx.client.getPlane()) {
                plane.iterator().forEach { row ->
                    row.iterator().forEach { tile ->
                        if (tile != null) {
                            if (tile.getScenery().isNotEmpty()) {
                                var count = 0
                                tile.getScenery().iterator().forEach {
                                    if (it != null && it.getTag() > 0) {
                                        count += 1
                                        // Print out the polygons for the models
                                        if (false) {
                                            val tilePolygon =
                                                Calculations.getCanvasTileAreaPoly(ctx,
                                                        it.getCenterX(),
                                                        it.getCenterY()
                                                )
                                            g.color = Color.ORANGE
                                            g.drawPolygon(tilePolygon)
                                        }

                                        val go = GameObject(it, ctx = ctx)
                                        val globalPos = go.getGlobalLocation()

                                        val point =
                                            Calculations.worldToScreen(
                                                    it.getCenterX(),
                                                    it.getCenterY(),
                                                    planeInt,
                                                    ctx

                                            )


                                        //Printing out the model and the hull
                                        if (localPlayer.distanceTo(globalPos) < 5) {
                                            var model = it.getEntity()
                                            if(!(model is Model)){
                                                model = model.getModel()
                                            }
                                            if (model is Model) {
                                                val positionInfo =
                                                        ObjectPositionInfo(
                                                                it.getCenterX(),
                                                                it.getCenterY(),
                                                                it.getOrientation()
                                                        )

                                                val modelTriangles =
                                                        getTrianglesFromModel(
                                                                positionInfo,
                                                                model,
                                                                ctx

                                                        )
                                                g.color = Color.RED
                                                modelTriangles.forEach {
                                                    g.drawPolygon(it)
                                                }
                                                val hull = getConvexHullFromModel(
                                                        positionInfo,
                                                        model,
                                                        ctx

                                                )
                                                g.color = Color.CYAN
                                                g.drawPolygon(hull)


                                            }
                                        }

                                        if (point.x != -1 && point.y != -1 && Calculations.isOnscreen(
                                                        ctx,point
                                                )
                                        ) {
                                            g.color = Color.GREEN
                                            val id = it.getTag().shr(17).and(0x7fff).toInt()
                                            val rawID = it.getTag().shr(14).and(0x7fff)
                                            //                                            println("${it.getWidgetID()},$rawID,$widgetID,${it.getRenderable().getWidgetID()}")
                                            val objectComposite =
                                                    getObjectComposite(sceneData, id)
                                            val point2 =
                                                    Calculations.worldToScreen(
                                                            it.getCenterX(),
                                                            it.getCenterY(),
                                                            it.getEntity().getHeight(),
                                                            ctx

                                                    )

                                            //Add offset on the Y so Things on the same tile do not stack
                                            val offsetY = (count-1) * 30
                                            //For now only filter objects near m
                                            if (localPlayer.distanceTo(globalPos) < 5)
                                                g.drawString(
                                                        objectComposite?.getName() + "(${go.id})(${globalPos.x},${globalPos.y}",
                                                        point2.x,
                                                        point2.y + offsetY
                                                )

                                            //Filter only near me: if(abs(globalPos.x - localPlayer.getGlobalLocation().x) <5 && abs(globalPos.y - localPlayer.getGlobalLocation().y) < 5)
                                        }
                                    }
                                }
                            }


                            val globalPos =
                                Tile(
                                        tile.getX() + ctx.client.getBaseX(),
                                        tile.getY() + ctx.client.getBaseY(),
                                        ctx = ctx
                                )

//                        println("Tile: ${tile.getCenterX()},${tile.getCenterY()} locGlob: ${localPlayer.getGlobalLocation()} localReg: ${localPlayer.getRegionalLocation()}")
                            // Display the wall object
                            if (tile.getWall() != null && localPlayer.distanceTo(globalPos) < 5) {
                                val wall = tile.getWall()
                                val wo = GameObject(wallObject = wall,ctx=ctx)

                                g.color = Color.GREEN
                                val id = wall.getTag().shr(17).and(0x7fff).toInt()
                                //                                            println("${it.getWidgetID()},$rawID,$widgetID,${it.getRenderable().getWidgetID()}")
                                val objectComposite =
                                        getObjectComposite(sceneData, id)

                                val model = wall.getEntity1()
                                if (model is Model) {
                                    val positionInfo =
                                            ObjectPositionInfo(
                                                    wall.getX(),
                                                    wall.getY(),
                                                    wall.getOrientationA()
                                            )

                                    val modelTriangles =
                                            getTrianglesFromModel(
                                                    positionInfo,
                                                    model,
                                                    ctx

                                            )
                                    g.color = Color.RED
                                    modelTriangles.forEach {
                                        g.drawPolygon(it)
                                    }
                                    val hull = getConvexHullFromModel(
                                            positionInfo,
                                            model,
                                            ctx

                                    )
                                    g.color = Color.CYAN
                                    g.drawPolygon(hull)

                                }
                                val point2 =
                                        Calculations.worldToScreen(
                                                wall.getX(),
                                                wall.getY(),
                                                wall.getEntity1().getHeight(),
                                                ctx
                                        )
                                g.drawString(
                                        objectComposite?.getName() + "(${wo.id})(${globalPos.x},${globalPos.y}",
                                        point2.x,
                                        point2.y
                                )
                            }
                        }
                    }
                }
            }
            planeInt += 1
        }
    }
}

fun getObjectComposite(
    objectCache: EvictingDualNodeHashTable,
    gameObjectId: Int
): LocType? {
    var desiredGameObject1: LocType? = null
    objectCache.getHashTable().getBuckets().iterator().forEach { bucketItem ->
        if (bucketItem != null) {

            var objectComposite = bucketItem.getNext()
            while (objectComposite != null
                && objectComposite is LocType
                && objectComposite != bucketItem
            ) {
                if (objectComposite.getKey() > 0
                    && objectComposite.getKey().toInt() == gameObjectId
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