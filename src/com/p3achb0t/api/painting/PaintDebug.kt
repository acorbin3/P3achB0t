package com.p3achb0t.api.painting


import java.awt.Graphics
import java.awt.Rectangle


class PaintDebug {
    companion object {
        var isDebugTextOn = false
        var isPlayerPaintOn = false
        var isNPCPaintOn = false
        var isGroundItemsOn = false
        var isCtrlPressed = false
    }
}

fun drawRect(g: Graphics, rect: Rectangle) {
    g.drawRect(rect.x, rect.y, rect.width, rect.height)
}

/*
fun debugPaint(client: com.p3achb0t._runestar_interfaces.Client): PaintListener {
    return object : PaintListener {
        override fun onPaint(g: Graphics) {
            try {
                g.color = Color.white
                MainApplet.mouseEvent?.x?.let { MainApplet.mouseEvent?.y?.let { it1 -> g.drawRect(it, it1, 5, 5) } }
                if (PaintDebug.isDebugTextOn)
                    drawDebugText(g, client)

                if (client.getGameState() == 30) {
                    if (!Bank(client).isOpen()) {
                        if (PaintDebug.isGroundItemsOn)
                            groundItemsPaint(g,client )
                        if (PaintDebug.isPlayerPaintOn)
                            playerPaint(g, client)
                        if (PaintDebug.isNPCPaintOn)
                            paintNPCs(g,client )
                        widgetBlockingPaint(g)
                        ///////Object paint//////////
//                        gameObjectPaint(g)

                    }

                    if (Bank(client).isOpen()) {
                        val items = Bank(client).getAll()
                        items.forEach {
                            g.color = Color.ORANGE
                            g.drawRect(it.area.x, it.area.y, it.area.width, it.area.height)

                        }
                    }


                    rightClickMenuPaint(g, client)
                    inventoryPaint(g, client)
                    equipmentPaint(g, client)

                    // Paint minimap circle
                    try {
                        val circle = MiniMap(client).getMapArea()
                        g.color = Color.RED
                        g.drawPolygon(circle)
                    } catch (e: Exception) {
                        println("Error: Minimap " + e.message)
                    }
                    // Paint continue
                    val dialog = Dialog(client).getDialogContinue()
                    if (dialog.widget != null) {
                        g.color = Color.ORANGE
                        drawRect(g, dialog.area)
                    }

                    // Paint on minimap
                    val local = client.getLocalPlayer()
                    val point = worldToMiniMap(local.getX(), local.getY(), client)
                    if (point != Point(-1, -1)) {
                        g.color = Color.red
                        g.fillRect(point.x, point.y, 4, 4)
                    }
                }
            } catch (e: Exception) {
                println("Error:  General  $e\n ${e.stackTrace} \n ${e.localizedMessage}")
            }
        }
    }
}*/