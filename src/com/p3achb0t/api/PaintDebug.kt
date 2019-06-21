package com.p3achb0t.api

import com.p3achb0t.Main
import com.p3achb0t.hook_interfaces.Npc
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

            ///////Object paint//////////
            //TODO - Look at the game objects in the region
//            val region = Main.clientData.getRegion()
//            region.getTiles().iterator().forEach { plane ->
//                plane.iterator().forEach { row ->
//                    row.iterator().forEach { tile ->
//                        if (tile != null) {
//                            if (tile.getObjects().isNotEmpty()) {
//                                val tilePolygon =
//                                    Calculations.getCanvasTileAreaPoly(
//                                        tile.getX(),
//                                        tile.getY()
//                                    )
//                                g.color = Color.ORANGE
//                                g.drawPolygon(tilePolygon)
//                                val point =
//                                    Calculations.worldToScreen(tile.getX(), tile.getY(), 0)
//                                if (point.x != -1 && point.y != -1 && Calculations.isOnscreen(
//                                        point
//                                    )
//                                ) {
//                                    g.color = Color.GREEN
//                                    g.drawString(tile.getObjects()[0].getId().toString(), point.x, point.y)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            region.getGameObjects().iterator().forEach {
//                if (it != null) {
//                    val tile = Calculations.getCanvasTileAreaPoly(it.getX(), it.getY())
//                    g.color = Color.ORANGE
//                    g.drawPolygon(tile)
//                    val point =
//                        Calculations.worldToScreen(it.getX(), it.getY(), it.getHeight())
//                    if (point.x != -1 && point.y != -1 && Calculations.isOnscreen(point)) {
//                        g.color = Color.GREEN
//                        g.drawString(it.getId().toString(), point.x, point.y)
//                    }
//                }
//            }


//                        println("")
//                        println("Model list size:" + modelList.size)

        }


    }
}