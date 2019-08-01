package com.p3achb0t.api.painting

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations.Companion.worldToMiniMap
import com.p3achb0t.api.wrappers.Bank
import com.p3achb0t.api.wrappers.Dialog
import com.p3achb0t.api.wrappers.MiniMap
import com.p3achb0t.hook_interfaces.Cache
import com.p3achb0t.hook_interfaces.Model
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

                if (Main.clientData.getGameState() == 30) {
                    if (!Bank.isOpen()) {

                        playerPaint(g)
                        paintNPCs(g)
                        widgetBlockingPaint(g)
                        ///////Object paint//////////
                        gameObjectPaint(g)
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

                    val local = Main.clientData.getLocalPlayer()
                    val point = worldToMiniMap(local.getLocalX(), local.getLocalY())
                    if (point != Point(-1, -1)) {
                        g.color = Color.red
                        g.fillRect(point.x, point.y, 3, 3)
                    }
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


        fun getAllObjectModels(objectModels: Cache): ArrayList<Model> {
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

    }
}