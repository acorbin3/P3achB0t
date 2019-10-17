package com.p3achb0t.api.painting

//import com.p3achb0t.interfaces.PaintListener
import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Calculations.Companion.worldToMiniMap
import com.p3achb0t.api.Context
import com.p3achb0t.api.painting.PaintDebug.Companion.selectedWidget
import com.p3achb0t.api.painting.PaintDebug.Companion.selectedWidgetItem
import com.p3achb0t.api.wrappers.Bank
import com.p3achb0t.api.wrappers.widgets.WidgetItem
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
        var selectedWidget: Component? = null
        var selectedWidgetItem: WidgetItem? = null
    }
}

fun drawRect(g: Graphics, rect: Rectangle) {
    g.drawRect(rect.x, rect.y, rect.width, rect.height)
}


fun debugPaint(ctx: Context, g: Graphics) {
    try {
        g.color = Color.white
        g.drawRect(ctx!!.mouse.getX(), ctx.mouse.getY(), 5, 5)

        if (selectedWidget?.getId() != ctx.selectedWidget?.getId()) {
            println("Widget Switched to ${ctx.selectedWidget?.getId()}")
            selectedWidget = ctx.selectedWidget
            selectedWidgetItem = WidgetItem(selectedWidget, ctx = ctx)
        }
        if (selectedWidgetItem != null) {
            selectedWidgetItem?.area?.let { g.drawRect(it.x, it.y, it.width, it.height) }
        }
        if (PaintDebug.isDebugTextOn)
            drawDebugText(g, ctx)

        if (ctx.client.getGameState() == 30) {
            if (!Bank(ctx).isOpen()) {
                if (PaintDebug.isGroundItemsOn)
                    groundItemsPaint(g, ctx)
                if (PaintDebug.isPlayerPaintOn)
                    playerPaint(g, ctx)
                if (PaintDebug.isNPCPaintOn)
                    paintNPCs(g, ctx)
                widgetBlockingPaint(g)
                ///////Object paint//////////
//                        gameObjectPaint(g)

            }

            if (Bank(ctx).isOpen()) {
                val items = Bank(ctx).getAll()
                items.forEach {
                    g.color = Color.ORANGE
                    g.drawRect(it.area.x, it.area.y, it.area.width, it.area.height)

                }
            }


            rightClickMenuPaint(g, ctx)
            inventoryPaint(g, ctx)
            equipmentPaint(g, ctx)

            // Paint minimap circle
            try {
                val circle = ctx.miniMap.getMapArea()
                g.color = Color.RED
                g.drawPolygon(circle)
            } catch (e: Exception) {
                println("Error: Minimap " + e.message)
            }
            // Paint continue
            val dialog = ctx.dialog.getDialogContinue()
            if (dialog.widget != null) {
                g.color = Color.ORANGE
                drawRect(g, dialog.area)
            }

            // Paint on minimap
            val local = ctx.client.getLocalPlayer()
            val point = worldToMiniMap(local.getX(), local.getY(), ctx)
            if (point != Point(-1, -1)) {
                g.color = Color.red
                g.fillRect(point.x, point.y, 4, 4)
            }
        }


    } catch (e: Exception) {
        println("Error:  General  $e\n ${e.stackTrace} \n ${e.localizedMessage}")
    }

}

