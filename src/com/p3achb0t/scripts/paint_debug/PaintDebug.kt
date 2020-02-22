package com.p3achb0t.scripts.paint_debug

import com.p3achb0t.api.DebugScript
import com.p3achb0t.api.wrappers.Bank
import com.p3achb0t.api.wrappers.utils.Calculations
import java.awt.Color
import java.awt.Graphics
import java.awt.Point
import java.awt.Rectangle

class PaintDebug: DebugScript() {
    companion object {
        var isDebugTextOn = true
        var isPlayerPaintOn = false
        var isNPCPaintOn = false
        var isGroundItemsOn = false
        var isGameObjectOn = false
        var isCanWalkDebug = false
        var isProjectileDebug = false
        val scriptName = "PaintDebug"
        fun drawRect(g: Graphics, rect: Rectangle) {
            g.drawRect(rect.x, rect.y, rect.width, rect.height)
        }
    }
    fun drawRect(g: Graphics, rect: Rectangle) {
        g.drawRect(rect.x, rect.y, rect.width, rect.height)
    }
    override fun draw(g: Graphics) {
        try {


            val debugX = 50
            val debugY = 10
            g.color = Color.white
            g.drawRect(ctx.mouse.getX(), ctx.mouse.getY(), 5, 5)
            g.drawString("               Debug options: ctrl-1 debug text:$isDebugTextOn, ctrl-2 NPCs:$isNPCPaintOn, ctrl-3 players:$isPlayerPaintOn",debugX,debugY)
            g.drawString("               ctrl-4 gameobject:$isGameObjectOn,ctrl-5 GndItems:$isGroundItemsOn, ctrl-6 can walk:$isCanWalkDebug",debugX,debugY +10)
            g.drawString("               ctrl-7 projectile:$isProjectileDebug,",debugX,debugY + 20)

            if(isCanWalkDebug)
                canWalkDebug(g,ctx)

            if (isDebugTextOn)
                drawDebugText(g, ctx)

            if (ctx.client.getGameState() == 30) {
                if(isGameObjectOn)
                    gameObjectPaint(g, ctx)

                if (!Bank(ctx).isOpen()) {
                    if (isGroundItemsOn)
                        groundItemsPaint(g, ctx)
                    if (isPlayerPaintOn)
                        playerPaint(g, ctx)
                    if (isNPCPaintOn)
                        paintNPCs(g, ctx)
                    if(isProjectileDebug)
                        projectilePaint(g,ctx)
                    widgetBlockingPaint(g)
                    ///////Object paint//////////
//                        gameObjectPaint(g)

                }

                if (Bank(ctx).isOpen()) {
                    val items = Bank(ctx).getAll()
                    items.forEach {
                        g.color = Color.ORANGE
                        g.drawRect(it.area.x, it.area.y, it.area.width, it.area.height)
                        g.color = Color.white
                        g.drawString(it.id.toString(),it.area.x + 2, it.area.y + 20 )

                    }
                }

//
//                rightClickMenuPaint(g, ctx)
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
                val point = Calculations.worldToMiniMap(local.getX(), local.getY(), ctx)
                if (point != Point(-1, -1)) {
                    g.color = Color.red
                    g.fillRect(point.x, point.y, 4, 4)
                }


            }


        } catch (e: Exception) {
            println("Error:  General  $e\n ${e.localizedMessage}")
            e.stackTrace.forEach {
                println("\t$it")
            }
        }
    }
}