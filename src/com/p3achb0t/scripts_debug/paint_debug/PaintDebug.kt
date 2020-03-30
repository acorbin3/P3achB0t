package com.p3achb0t.scripts_debug.paint_debug

import com.p3achb0t.api.DebugScript
import com.p3achb0t.api.wrappers.Bank
import java.awt.Color
import java.awt.Graphics
import java.awt.Point
import java.awt.Rectangle

class PaintDebug: DebugScript() {
    companion object {
        var isDebugTextOn = false
        var isPlayerPaintOn = false
        var isNPCPaintOn = false
        var isGroundItemsOn = false
        var isGameObjectOn = false
        var isCanWalkDebug = false
        var isProjectileDebug = false
        var isInventoryPaintingDebug = false
        val scriptName = "PaintDebug"
        var isZulrah = true
        var fps = 15
        var args = ""
        var key = ""
        fun drawRect(g: Graphics, rect: Rectangle) {
            g.drawRect(rect.x, rect.y, rect.width, rect.height)
        }
    }
    fun drawRect(g: Graphics, rect: Rectangle) {
        g.drawRect(rect.x, rect.y, rect.width, rect.height)
    }

    fun drawDebugOptions(g: Graphics,debugX: Int,  debugY: Int){
        val debugOptionsArr = arrayOf(
                "ctrl-1 debug text: $isDebugTextOn",
                "ctrl-2 NPCs: $isNPCPaintOn",
                "ctrl-3 players: $isPlayerPaintOn",
                "ctrl-4 gameobject: $isGameObjectOn",
                "ctrl-5 GndItems: $isGroundItemsOn",
                "ctrl-6 can walk: $isCanWalkDebug",
                "ctrl-7 projectile: $isProjectileDebug",
                "ctrl-9 inventory: $isInventoryPaintingDebug"
        )

        val vertical = true //could add a hotkey to toggle this on/off
        var debugOptionsY = 25
        var debugOptionsX = if(vertical) debugX + 500 else debugX //change this whatever positions u like
        g.drawString("Debug options",debugOptionsX,debugY)
        val itemsPerRow = 2
        var currRow = 0
        debugOptionsArr.forEachIndexed{i,debugOption ->
            var yOffset = 20
            if(vertical){
                debugOptionsY+=20
            }else{
                val metrics = g.getFontMetrics(g.font)
                debugOptionsX+= metrics.stringWidth(debugOption) + 30 //you can add padding here, if you want the rows to look nice and clean
                if(i % itemsPerRow == 0){
                    currRow+=1
                    debugOptionsX = debugX
                }
                yOffset = 20 * currRow
            }
            g.drawString(debugOption,debugOptionsX,debugOptionsY + yOffset)
        }
    }

    override fun draw(g: Graphics) {
        try {


            val debugX = 50
            val debugY = 25
            g.color = Color.white
            g.drawRect(ctx.mouse.getX(), ctx.mouse.getY(), 5, 5)
            drawDebugOptions(g,debugX,debugY)


            if(isCanWalkDebug)
                canWalkDebug(g,ctx)

            if (isDebugTextOn)
                drawDebugText(g, ctx)

//


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
                        g.color = Color.WHITE
                        var area: Rectangle = Rectangle(60, 70, 440, 315)
                        if(area.contains(Point(it.getBasePoint().x, it.getBasePoint().y))) {
                            g.font = g.font.deriveFont(9.5f)
                            g.drawString("${it.id}", it.getBasePoint().x + 5, it.getBasePoint().y)
                        }
//                        g.drawRect(it.area.x, it.area.y, it.area.width, it.area.height)

                    }
                }

//
//                rightClickMenuPaint(g, ctx)
                if(isInventoryPaintingDebug)
                    inventoryPaint(g, ctx)
//                equipmentPaint(g, ctx)

                // Paint minimap circle
//                try {
//                    val circle = ctx.miniMap.getMapArea()
//                    g.color = Color.RED
//                    g.drawPolygon(circle)
//                } catch (e: Exception) {
//                    println("Error: Minimap " + e.message)
//                }
                // Paint continue
//                val dialog = ctx.dialog.getDialogContinue()
//                if (dialog.widget != null) {
//                    g.color = Color.ORANGE
//                    drawRect(g, dialog.area)
//                }

                // Paint on minimap
//                val local = ctx.client.getLocalPlayer()
//                val point = Calculations.worldToMiniMap(local.getX(), local.getY(), ctx)
//                if (point != Point(-1, -1)) {
//                    g.color = Color.red
//                    g.fillRect(point.x, point.y, 4, 4)
//                }

            Thread.sleep(1000/fps.toLong())
            }


        } catch (e: Exception) {
            println("Error:  General  $e\n ${e.localizedMessage}")
            e.stackTrace.forEach {
                println("\t$it")
            }
        }
    }
}