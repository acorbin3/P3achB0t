package com.p3achb0t.api.painting

import com.p3achb0t.MainApplet
import com.p3achb0t.api.Calculations.Companion.worldToMiniMap
import com.p3achb0t.api.wrappers.Bank
import com.p3achb0t.api.wrappers.Client
import com.p3achb0t.api.wrappers.Dialog
import com.p3achb0t.api.wrappers.MiniMap
import com.p3achb0t.interfaces.PaintListener
import java.awt.Color
import java.awt.Graphics
import java.awt.Point
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


fun debugPaint(): PaintListener {
    return object : PaintListener {
        override fun onPaint(g: Graphics) {
            try {
                g.color = Color.white
                MainApplet.mouseEvent?.x?.let { MainApplet.mouseEvent?.y?.let { it1 -> g.drawRect(it, it1, 5, 5) } }
                if (PaintDebug.isDebugTextOn)
                    drawDebugText(g)

                if (Client.client.getGameState() == 30) {
                    if (!Bank.isOpen()) {
                        if (PaintDebug.isGroundItemsOn)
                            groundItemsPaint(g)
                        if (PaintDebug.isPlayerPaintOn)
                            playerPaint(g)
                        if (PaintDebug.isNPCPaintOn)
                            paintNPCs(g)
                        widgetBlockingPaint(g)
                        ///////Object paint//////////
//                        gameObjectPaint(g)

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
                    val point = worldToMiniMap(local.getX(), local.getY())
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
}