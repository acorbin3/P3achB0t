package com.p3achb0t.api.painting

import com.p3achb0t._runestar_interfaces.EvictingDualNodeHashTable
import com.p3achb0t._runestar_interfaces.Model
import com.p3achb0t.api.Calculations.Companion.worldToMiniMap
import com.p3achb0t.api.Calculations.Companion.worldToScreen
import com.p3achb0t.api.wrappers.*
import com.p3achb0t.interfaces.PaintListener
import java.awt.Color
import java.awt.Graphics
import java.awt.Point
import java.awt.Rectangle


fun drawRect(g: Graphics, rect: Rectangle) {
    g.drawRect(rect.x, rect.y, rect.width, rect.height)
}

fun debugPaint(): PaintListener {
    return object : PaintListener {
        override fun onPaint(g: Graphics) {
            try {
                drawDebugText(g)
//                        print("[")
//                        for (x in clientData.get_widgetHeights()) {
//                            print("$x,")
//                        }
//                        println("]")
//                        println(clientData.get_username() + " " + clientData.get_isWorldSelectorOpen())

                if (Client.client.getGameState() == 30) {
                    if (!Bank.isOpen()) {

                        playerPaint(g)
//                        paintNPCs(g)
                        widgetBlockingPaint(g)
                        ///////Object paint//////////
//                        gameObjectPaint(g)
//TODO - ground items issue
//                    try {
//                        val groundItems = GroundItems.getAllItems()
//                        groundItems.forEach {
//                            val point1 = Calculations.worldToScreen(it.position.x, it.position.y, it.position.plane)
//                            if (point1.x != -1 && point1.y != -1 && Calculations.isOnscreen(point1)) {
//                                g.color = Color.GREEN
//                                g.drawString("(${it.id})", point1.x, point1.y - 20) // moving widgetID up 20 pixels
//                                val modelTriangles = it.getTriangles()
//                                g.color = Color.RED
//                                modelTriangles.forEach {
//                                    g.drawPolygon(it)
//                                }
//                                val hull = it.getConvexHull()
//                                g.color = Color.CYAN
//                                g.drawPolygon(hull)
//                            }
//                        }
//                    }catch(e: Exception){ println("Error: Gound items " + e.message)}
                    }

                    if (Bank.isOpen()) {
                        val items = Bank.getAll()
                        items.forEach {
                            g.color = Color.ORANGE
                            g.drawRect(it.area.x, it.area.y, it.area.width, it.area.height)

                        }
                    }


                    rightClickMenuPaint(g)

                    inventoryPaint(g)

                    equipmentPaint(g)

                    // Paint minimap circle
                    try {
                        val circle = MiniMap.getMapArea()
                        g.color = Color.RED
                        g.drawPolygon(circle)
                    } catch (e: Exception) {
                        println("Error: Minimap " + e.message)
                    }
                    // Paint continue
                    val dialog = Dialog.getDialogContinue()
                    if (dialog.widget != null) {
                        g.color = Color.ORANGE
                        drawRect(g, dialog.area)
                    }

                    // Paint on minimap

                    val local = Client.client.getLocalPlayer()
                    println("Local ${local.getX()},${local.getY()}")
                    val point = worldToMiniMap(local.getX(), local.getY())
                    if (point != Point(-1, -1)) {
                        g.color = Color.red
                        g.fillRect(point.x, point.y, 4, 4)
                    }
                    var local2 = Players.getLocal()
                    println("${local2.getLocalLocation()}  ${local2.getGlobalLocation()}  ${local2.getRegionalLocation()}  ${local2.player.getX()},${local2.player.getY()}")
                    val point2 = worldToScreen(local2.player.getX(), local2.player.getY(), local.getHeight())
                    g.drawString(local.getUsername().getCleanName(), point2.x, point2.y)
                }

            } catch (e: Exception) {
                println("Error:  General  $e\n ${e.stackTrace} \n ${e.localizedMessage}")
            }
        }


        fun drawCenteredCircle(g: Graphics, x: Int, y: Int, r: Int) {
            var x = x
            var y = y
            x -= r / 2
            y -= r / 2
            g.color = Color.PINK
            g.drawOval(x, y, r, r)
        }


        fun getAllObjectModels(objectModels: EvictingDualNodeHashTable): ArrayList<Model> {
            val modelList = ArrayList<Model>()
            objectModels.getHashTable().getBuckets().iterator().forEach { bucket ->
                if (bucket != null) {
//                    println(bucket.getWidgetID())
                    var model = bucket.getNext()
                    while (model != null && model is Model && model != bucket) {
                        modelList.add(model)
                        model = model.getNext()
                    }


                }
            }
            if (modelList.isNotEmpty()) {
                println("Models")
                modelList.forEach { println(it.getKey()) }
                println("--")
            }
            return modelList
        }

        fun getObjectsModel(objectModels: EvictingDualNodeHashTable, renderModelID: Int): Model? {
            var desiredModel: Model? = null
            objectModels.getHashTable().getBuckets().iterator().forEach { bucket ->
                if (bucket != null) {
                    var model = bucket.getNext()
                    while (model != null && model is Model) {

                        if (model.getKey() > 0) {
                            println(
                                model.getKey().toString() + " " + model.getKey().shr(17).and(0x7fff) + " ${model.getKey().shr(
                                    16
                                )} ${model.getKey().shr(15)} ${model.getKey().shr(14)}"
                            )
                        }
                        if (model.getKey().toInt() == renderModelID) {
                            desiredModel = model as Model
                        }
                        model = model.getNext()
                    }

                }
            }

            println("--")
            return desiredModel
        }

    }
}