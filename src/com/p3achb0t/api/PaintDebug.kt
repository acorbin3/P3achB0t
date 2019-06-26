package com.p3achb0t.api

import com.p3achb0t.Main
import com.p3achb0t.hook_interfaces.*
import com.p3achb0t.interfaces.PaintListener
import java.awt.Color
import java.awt.Graphics
import java.awt.Point

fun debugPaint(): PaintListener {
    return object : PaintListener {
        override fun onPaint(g: Graphics) {
            g.color = Color.white
            g.drawString("Mouse x:${Main.mouseEvent?.x} y:${Main.mouseEvent?.y}", 50, 50)
            g.drawString("clientData.gameCycle :${Main.clientData.getGameCycle()}", 50, 60)
            g.drawString("Game State:: ${Main.clientData.getGameState()}", 50, 70)
            g.drawString("clientData.loginState :${Main.clientData.getLoginState()}", 50, 80)
            g.drawString("Account status :${Main.clientData.getAccountStatus()}", 50, 90)
//                        g.drawString("cameraX :${clientData.getCameraX()}", 50, 100)
//                        g.drawString("cameraY :${clientData.getCameraY()}", 50, 110)
            Main.mouseEvent?.x?.let { Main.mouseEvent?.y?.let { it1 -> g.drawRect(it, it1, 5, 5) } }
//                        print("[")
//                        for (x in clientData.get_widgetHeights()) {
//                            print("$x,")
//                        }
//                        println("]")
//                        println(clientData.get_username() + " " + clientData.get_isWorldSelectorOpen())


            ///////Player paint//////////
            g.color = Color.GREEN
            val players = Main.clientData.getPlayers()
            var count = 0
            var point = Point(200, 50)
            players.iterator().forEach { _player ->
                if (_player != null && _player.getLevel() > 0 && _player.getComposite().getStaticModelID() > 0) {
//                                print("${_player.getName()} ${_player.getLevel()} x:${_player.getLocalX()} y: ${_player.getLocalY()}, ")
//                                print("Queue size: ${_player.getQueueSize()}")

                    count += 1
                    val point = Calculations.worldToScreen(
                        _player.getLocalX(),
                        _player.getLocalY(),
                        _player.getModelHeight()
                    )
                    if (point.x != -1 && point.y != -1 && Calculations.isOnscreen(point)) {
                        g.color = Color.GREEN
                        g.drawString(_player.getName().getName(), point.x, point.y)
                    }
                    val polygon = getActorTriangles(
                        _player,
                        Main.clientData.getPlayerModelCache(),
                        _player.getComposite().getAnimatedModelID()
                    )
                    g.color = Color.YELLOW
                    polygon.forEach {
                        g.drawPolygon(it)
                    }
                    val ch = getConvexHull(
                        _player,
                        Main.clientData.getPlayerModelCache(),
                        _player.getComposite().getAnimatedModelID()
                    )
                    g.color = Color.RED
                    g.drawPolygon(ch)
                    val tile = Calculations.getCanvasTileAreaPoly(
                        _player.getLocalX(),
                        _player.getLocalY()
                    )
                    g.color = Color.CYAN
                    g.drawPolygon(tile)
                    g.color = Color(0, 0, 0, 50)
                    g.fillPolygon(tile)
                }
                point.y += 20
            }

            ///////NPC paint//////////
            count = 0
            val localNpcs = Main.clientData.getLocalNPCs()
            var npc: Npc? = null
            localNpcs.iterator().forEach {
                if (it != null) {
                    npc = it

//                                print("Name: ${it.getComposite().getName()}, ID:${it.getComposite().getNpcComposite_id()} x:${it.getLocalX()} y:${it.getLocalY()},")
                    count += 1
                    val point =
                        Calculations.worldToScreen(
                            it.getLocalX(),
                            it.getLocalY(),
                            it.getModelHeight()
                        )
                    if (point.x != -1 && point.y != -1 && Calculations.isOnscreen(point)) {
                        g.color = Color.GREEN
                        g.drawString(it.getComposite().getName(), point.x, point.y)
                    }

                    val polygon = npc?.getComposite()?.getNpcComposite_id()?.toLong()?.let { it1 ->
                        getActorTriangles(
                            npc, Main.clientData.getNpcModelCache(),
                            it1
                        )
                    }
                    g.color = Color.BLUE
                    polygon?.forEach {
                        g.drawPolygon(it)
                    }
                    val ch = getConvexHull(
                        npc,
                        Main.clientData.getNpcModelCache(),
                        npc!!.getComposite().getNpcComposite_id().toLong()
                    )
                    g.color = Color.PINK
                    g.drawPolygon(ch)

                    val tile =
                        Calculations.getCanvasTileAreaPoly(it.getLocalX(), it.getLocalY())
                    g.color = Color.CYAN
                    g.drawPolygon(tile)
                    g.color = Color(0, 0, 0, 50)
                    g.fillPolygon(tile)

                }
            }
            val sceneData = Main.clientData.getObjectCompositeCache()


            ///////Object paint//////////
            if (false) {
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
                                            val tilePolygon =
                                                Calculations.getCanvasTileAreaPoly(
                                                    it.getX(),
                                                    it.getY()
                                                )
                                            g.color = Color.ORANGE
                                            g.drawPolygon(tilePolygon)
                                            val point =
                                                Calculations.worldToScreen(it.getX(), it.getY(), tile.getPlane())
                                            if (point.x != -1 && point.y != -1 && Calculations.isOnscreen(
                                                    point
                                                )
                                            ) {
                                                g.color = Color.GREEN
                                                val id = it.getId().shr(17).and(0x7fff).toInt()
                                                val rawID = it.getId().shr(14).and(0x7fff)
//                                            println("${it.getId()},$rawID,$id,${it.getRenderable().getId()}")
                                                val objectComposite = getObjectComposite(sceneData, id)
                                                g.drawString(objectComposite?.getName() + "($id)", point.x, point.y)
                                            }

                                            //Printing out the model and the hull
                                            val model = it.getRenderable()
                                            if (model is Model) {
                                                val positionInfo =
                                                    ObjectPositionInfo(it.getX(), it.getY(), it.getOrientation())

                                                val modelTriangles = getTrianglesFromModel(positionInfo, model)
                                                g.color = Color.RED
                                                modelTriangles.forEach {
                                                    g.drawPolygon(it)
                                                }
                                                val hull = getConvexHullFromModel(positionInfo, model)
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
            val groundItems = Main.clientData.getGroundItemList()
            groundItems.forEach { x ->
                x.iterator().forEach { y ->
                    y.iterator().forEach { item ->
                        if (item != null) {
                            var gi = item.getCurrent()
                            if (gi != null) {
                                if (gi is Item) {
                                    println("Found Item: ${gi.getId()}")
                                }
                                if (gi is ItemComposite) {
                                    println("Found Item Composite: ${gi.getId()}")
                                }
                                if (gi is ItemNode) {
                                    println("Found Item Node: ${gi.getId()}")
                                }
                                if (gi is ItemLayer) {
                                    println("Found Item Layer: ${gi.getId()}")
                                }
                            }
                        }
                    }
                }
            }

        }

        fun getAllObjectModels(objectModels: Cache): ArrayList<Model> {
            val modelList = ArrayList<Model>()
            objectModels.getHashTable().getBuckets().iterator().forEach { bucket ->
                if (bucket != null) {
//                    println(bucket.getId())
                    var model = bucket.getNext()
                    while (model != null && model is Model && model.getId() > 0) {
                        modelList.add(model)
                        model = model.getNext()
                    }


                }
            }
            if (modelList.isNotEmpty()) {
                println("Models")
                modelList.forEach { println(it.getId()) }
                println("--")
            }
            return modelList
        }

        fun getObjectsModel(objectModels: Cache, renderModelID: Int): Model? {
            var desiredModel: Model? = null
            objectModels.getHashTable().getBuckets().iterator().forEach { bucket ->
                if (bucket != null) {
                    var model = bucket.getNext()
                    while (model != null && model is Model) {

                        if (model.getId() > 0) {
                            println(
                                model.getId().toString() + " " + model.getId().shr(17).and(0x7fff) + " ${model.getId().shr(
                                    16
                                )} ${model.getId().shr(15)} ${model.getId().shr(14)}" + " UUID Count: ${(model as Model).getUidCount()}"
                            )
                        }
                        if (model.getId().toInt() == renderModelID) {
                            desiredModel = model as Model
                        }
                        model = model.getNext()
                    }

                }
            }

            println("--")
            return desiredModel
        }

        private fun getObjectComposite(
            objectCache: Cache,
            gameObjectId: Int
        ): ObjectComposite? {
            var desiredGameObject1: ObjectComposite? = null
            objectCache.getHashTable().getBuckets().iterator().forEach { bucketItem ->
                if (bucketItem != null) {

                    var objectComposite = bucketItem.getNext()
                    while (objectComposite != null
                        && objectComposite is ObjectComposite
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


    }
}