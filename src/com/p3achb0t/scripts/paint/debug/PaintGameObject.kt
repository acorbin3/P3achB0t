package com.p3achb0t.scripts.paint.debug

import com.p3achb0t.api.Context
import com.p3achb0t.api.interfaces.EvictingDualNodeHashTable
import com.p3achb0t.api.interfaces.LocType
import com.p3achb0t.api.interfaces.Model
import com.p3achb0t.api.interfaces.Wall
import com.p3achb0t.api.script.PaintScript
import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.wrappers.GameObject
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo
import com.p3achb0t.api.wrappers.utils.getConvexHullFromModel
import com.p3achb0t.api.wrappers.utils.getTrianglesFromModel
import java.awt.Color
import java.awt.Graphics
import java.awt.Point

@ScriptManifest("Debug", "GameObject Helper", "Bot Team", "0.1")
class PaintGameObject : PaintScript() {
    override fun draw(g: Graphics) {
        if (ctx.client.getGameState() == 30) {
            try {
                gameObjectPaint(g, ctx)
            }catch (e: Exception){

            }
        }
    }

    fun gameObjectPaint(g: Graphics, ctx: Context) {
        if (true) {
            val sceneData = ctx.client.getObjType_cachedModels()
            val region = ctx.client.getScene()
            val localPlayer = ctx.players.getLocal()
            var planeInt = 0
            region.getTiles().iterator().forEach { plane ->
//            if (planeInt == ctx.client.getPlane()) {
                if (true) {
                    plane.iterator().forEach { row ->

                        row.iterator().forEach { tile ->
                            if (tile != null) {

                                //Floor decoration
                                val floorDecoration = tile.getFloorDecoration()

                                if (floorDecoration != null) {
                                    val go_fd = GameObject(floorDecoration = floorDecoration, ctx = ctx)
                                    val globalPos_fd = go_fd.getGlobalLocation()
                                    if (localPlayer.distanceTo(globalPos_fd) < 5
                                        && planeInt == ctx.players.getLocal().player.getPlane()
                                    ) {

                                        try {
                                            val floorModel = go_fd.model!!
                                            val positionInfo =
                                                ObjectPositionInfo(
                                                    floorDecoration.getX(),
                                                    floorDecoration.getY(),
                                                    0
                                                )


                                            g.color = Color.pink
                                            if (go_fd.isMouseOverObj()) {
                                                val modelTriangles =
                                                    getTrianglesFromModel(
                                                        positionInfo,
                                                        floorModel,
                                                        ctx

                                                    )

                                                modelTriangles.forEach {
                                                    g.drawPolygon(it)
                                                }

                                                val hull = getConvexHullFromModel(
                                                    positionInfo,
                                                    floorModel,
                                                    ctx

                                                )
                                                g.color = Color.darkGray
                                                g.drawPolygon(hull)

                                                g.color = Color.YELLOW
                                                val point2 =
                                                    Calculations.worldToScreen(
                                                        floorDecoration.getX(),
                                                        floorDecoration.getY(),
                                                        floorDecoration.getEntity().getHeight(),
                                                        ctx
                                                    )
                                                g.drawString("FD-" +
                                                    go_fd.name + "(${go_fd.id})(${globalPos_fd.x},${globalPos_fd.y}) Loc:(${go_fd.getLocalLocation().x},${go_fd.getLocalLocation().y})tag:${go_fd.floorDecoration?.getTag()}",
                                                    point2.x,
                                                    point2.y
                                                )
                                            }
                                        } catch (e: Exception) {

                                        }
                                    }
                                }

                                //Scene entitys

                                if (tile.getScenery().isNotEmpty()) {
                                    var count = 0
                                    tile.getScenery().iterator().forEach {
                                        if (it != null && it.getTag() > 0) {

                                            val testID = 23145
                                            val id2 = it.getTag().ushr(17).and(4294967295L).toInt()
                                            if (id2 == testID) {
                                                println("log found")
                                            }
                                            count += 1
                                            // Print out the polygons for the models
                                            if (false) {
                                                val tilePolygon =
                                                    Calculations.getCanvasTileAreaPoly(
                                                        ctx,
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
                                            if (localPlayer.distanceTo(globalPos) < 10
                                                && planeInt == ctx.players.getLocal().player.getPlane()
                                            ) {
                                                var model = it.getEntity()
                                                if (!(model is Model)) {
                                                    model = model.getModel()
                                                }
                                                if (model is Model) {
                                                    val positionInfo =
                                                        ObjectPositionInfo(
                                                            it.getCenterX(),
                                                            it.getCenterY(),
                                                            it.getOrientation()
                                                        )

                                                    val mousePoint = Point(
                                                        ctx?.mouse?.getX() ?: -1, ctx?.mouse?.getY()
                                                            ?: -1
                                                    )
                                                    val hull = getConvexHullFromModel(
                                                        positionInfo,
                                                        model,
                                                        ctx

                                                    )
                                                    if (hull.contains(mousePoint)) {
                                                        val modelTriangles =
                                                            getTrianglesFromModel(
                                                                positionInfo,
                                                                model,
                                                                ctx

                                                            )
                                                        g.color = Color.CYAN
                                                        g.drawPolygon(hull)
                                                        g.color = Color.RED
                                                        modelTriangles.forEach {
                                                            g.drawPolygon(it)
                                                        }
                                                        // PAint the name
                                                        if (point.x != -1 && point.y != -1 && Calculations.isOnscreen(
                                                                ctx, point
                                                            )
                                                        ) {
                                                            g.color = Color.GREEN
                                                            val point2 =
                                                                Calculations.worldToScreen(
                                                                    it.getCenterX(),
                                                                    it.getCenterY(),
                                                                    it.getEntity().getHeight(),
                                                                    ctx

                                                                )
                                                            val localPos = go.getLocalLocation()

                                                            //Add offset on the Y so Things on the same tile do not stack
                                                            val offsetY = (count - 1) * 30
                                                            //For now only filter objects near m
                                                            if (localPlayer.distanceTo(globalPos) < 10
                                                                && planeInt == ctx.players.getLocal().player.getPlane()
                                                            )
                                                                g.drawString("SE-" +
                                                                    go.name + "(${go.id})(${globalPos.x},${globalPos.y}) l(${localPos.x},${localPos.y})tag:${go.sceneryObject?.getTag()}",
                                                                    point2.x,
                                                                    point2.y + offsetY
                                                                )

                                                            //Filter only near me: if(abs(globalPos.x - localPlayer.getGlobalLocation().x) <5 && abs(globalPos.y - localPlayer.getGlobalLocation().y) < 5)
                                                        }
                                                    }
                                                }
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

                                //print out wall decorations
                                if (tile.getWallDecoration() != null && localPlayer.distanceTo(globalPos) < 5) {
                                    val wallDecoration = tile.getWallDecoration()
                                    val wd = GameObject(wallDecoration = wallDecoration, ctx = ctx)
                                    val id = wallDecoration.getTag().ushr(17).and(4294967295L).toInt()
//                                    val objectComposite =
//                                        getObjectComposite(sceneData, id)
                                    val positionInfo =
                                        ObjectPositionInfo(
                                            wallDecoration.getX(),
                                            wallDecoration.getY(),
                                            wallDecoration.getOrientation()
                                        )
                                    if (wallDecoration != null
                                        && wallDecoration.getEntity1() != null
                                    ) {
                                        if (wallDecoration.getEntity1() is Model) {
                                            val model = wallDecoration.getEntity1() as Model
                                            val modelTriangles =
                                                getTrianglesFromModel(
                                                    positionInfo,
                                                    model,
                                                    ctx

                                                )
                                            g.color = Color.PINK
                                            modelTriangles.forEach {
                                                g.drawPolygon(it)
                                            }
                                        }
                                    }
                                    val point2 =
                                        Calculations.worldToScreen(
                                            wallDecoration.getX(),
                                            wallDecoration.getY(),
                                            wallDecoration.getEntity1().getHeight(),
                                            ctx
                                        )
                                    g.drawString("WD-" +
                                            wd.name + "(${wd.id})(${globalPos.x},${globalPos.y} Loc:(${wd.getLocalLocation().x},${wd.getLocalLocation().y}) tag:${wd.wallDecoration?.getTag()}",
                                        point2.x,
                                        point2.y
                                    )
                                }


//                        println("Tile: ${tile.getCenterX()},${tile.getCenterY()} locGlob: ${localPlayer.getGlobalLocation()} localReg: ${localPlayer.getRegionalLocation()}")
                                // Display the wall object
                                if (tile.getWall() != null && localPlayer.distanceTo(globalPos) < 5 && false) {
                                    val wall = tile.getWall()
                                    val wo = GameObject(wallObject = wall, ctx = ctx)

                                    if (wo.isMouseOverObj()) {
                                        g.color = Color.CYAN
                                        val id = wall.getTag().ushr(17).and(4294967295L).toInt()

                                        if (wall.getEntity1() != null) {
                                            val model = wall.getEntity1().getModel()
                                            if (model != null)
                                                paintWallModel(wall, model, ctx, g)
                                        }

                                        if (wall.getEntity2() != null) {
                                            val model2 = wall.getEntity2().getModel()
                                            if (model2 != null)
                                                paintWallModel(wall, model2, ctx, g)
                                        }

                                        val point2 =
                                            Calculations.worldToScreen(
                                                wall.getX(),
                                                wall.getY(),
                                                wall.getEntity1().getHeight(),
                                                ctx
                                            )
                                        g.drawString("WO-" +
                                            wo.name + "(${wo.id})(${globalPos.x},${globalPos.y} Loc:(${wo.getLocalLocation().x},${wo.getLocalLocation().y}) tag:${wo.wallObject?.getTag()}",
                                            point2.x,
                                            point2.y
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                planeInt += 1
            }
        }
    }

    private fun paintWallModel(wall: Wall, model: Model, ctx: Context, g: Graphics) {
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
        val mousePoint = Point(ctx?.mouse?.getX() ?: -1, ctx?.mouse?.getY() ?: -1)
        val hull = getConvexHullFromModel(
            positionInfo,
            model,
            ctx

        )
        g.color = Color.CYAN
        if (hull.contains(mousePoint)) {
            modelTriangles.forEach {
                g.drawPolygon(it)
            }
            g.color = Color.CYAN
            g.drawPolygon(hull)
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

}




