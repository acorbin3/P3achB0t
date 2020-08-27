package com.p3achb0t.scripts.paint.debug

import com.p3achb0t.api.script.PaintScript
import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.wrappers.Bank
import java.awt.Color
import java.awt.Graphics
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.*

@ScriptManifest("Debug","Total Debug Paint","P3aches", "0.1")
class TotalDebugPaint: PaintScript(), KeyListener, MouseWheelListener, MouseListener {
    var isDebugTextOn = false
    var isPlayerPaintOn = false
    var isNPCPaintOn = false
    var isGroundItemsOn = false
    var isGameObjectOn = false
    var isCanWalkDebug = false
    var isProjectileDebug = false
    var isInventoryPaintingDebug = false
    var isPaintEquipment = false
    var isBankPaintDebug = false
    var isClientDebugging = false

    lateinit var paintEquipment: PaintEquipment
    lateinit var walkHelper: PaintWalkHelper
    lateinit var paintGameObject: PaintGameObject
    lateinit var paintPlayer: PaintPlayer
    lateinit var paintNPC: PaintNpc
    lateinit var paintGroundItems: PaintGroundItems
    lateinit var widgetBlocking: PaintWidgetBlocking
    lateinit var paintInventory: PaintInventory

    override fun start() {
        paintEquipment = PaintEquipment()
        paintEquipment.ctx = ctx
        walkHelper = PaintWalkHelper()
        walkHelper.ctx = ctx
        paintGameObject = PaintGameObject()
        paintGameObject.ctx = ctx
        paintPlayer = PaintPlayer()
        paintPlayer.ctx = ctx
        paintGroundItems = PaintGroundItems()
        paintGroundItems.ctx = ctx
        widgetBlocking = PaintWidgetBlocking()
        widgetBlocking.ctx = ctx
        paintInventory = PaintInventory()
        paintInventory.ctx = ctx
        paintNPC = PaintNpc()
        paintNPC.ctx = ctx
        super.start()
    }
    
    override fun draw(g: Graphics) {
        try {

            val debugX = 50
            val debugY = 25
            g.color = Color.white
            g.drawRect(ctx.mouse.getX(), ctx.mouse.getY(), 5, 5)
             drawDebugOptions(g,debugX,debugY)


            if(isCanWalkDebug)
                walkHelper.draw(g)

            if (isDebugTextOn)
                drawDebugText(g, ctx)
            if(isClientDebugging)
                drawClientDebugText(g,ctx)

//


            if (ctx.client.getGameState() == 30) {
                if(isGameObjectOn)
                    paintGameObject.draw(g)

                if (!ctx.bank.isOpen()) {
                    if (isGroundItemsOn)
                        paintGroundItems.draw(g)
                    if (isPlayerPaintOn)
                        paintPlayer.draw(g)
                    if (isNPCPaintOn)
                        paintNPC.draw(g)
                    if(isProjectileDebug)
                        projectilePaint(g,ctx)
                    widgetBlocking.draw(g)
                }

                if(isBankPaintDebug) {
                    if (ctx.bank.isOpen()) {
                        val items = Bank(ctx).getAll()
                        items.forEach {
                            g.color = Color.WHITE
                            var area: Rectangle = Rectangle(60, 70, 440, 315)
                            if (area.contains(Point(it.getBasePoint().x, it.getBasePoint().y))) {
                                g.font = g.font.deriveFont(9.5f)
                                g.drawString("${it.id}", it.getBasePoint().x + 5, it.getBasePoint().y)
                            }
//                        g.drawRect(it.area.x, it.area.y, it.area.width, it.area.height)

                        }
                    }
                }

//
//                rightClickMenuPaint(g, ctx)
                if(isInventoryPaintingDebug)
                    paintInventory.draw(g)
                if(isPaintEquipment)
                    paintEquipment.draw(g)

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

            }


        } catch (e: Exception) {
            println("Error:  General  $e\n ${e.localizedMessage}")
            e.stackTrace.forEach {
                println("\t$it")
            }
        }
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
                "ctrl-9 inventory: $isInventoryPaintingDebug",
                "ctrl-0 equipment: $isPaintEquipment",
                "ctrl-q bank: $isBankPaintDebug",
                "ctrl-w client debugs:$isClientDebugging"

        )

        val vertical = true //could add a hotkey to toggle this on/off
        var debugOptionsY = 25
        var debugOptionsX = if(vertical) debugX + 500 else debugX //change this whatever positions u like
        val savedFont = g.font
        g.font = g.font.deriveFont(10F)

        g.drawString("Debug options",debugOptionsX,debugY)
        val itemsPerRow = 2
        var currRow = 0
        debugOptionsArr.forEachIndexed{i,debugOption ->
            var yOffset = 20
            if(vertical){
                debugOptionsY+=10
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

        g.font = savedFont
    }

    override fun keyTyped(e: KeyEvent?) {
    }

    override fun keyPressed(e: KeyEvent?) {
        if(e?.isControlDown == true ){
            //In order to get other charcter keys to work we need to convert to the ascii decimal number and then use that
            // as the string comparison. This seemed to get letters to work fine.
            // NOTE: if you want to add more characters, they just need to be capitalized
            when (e.keyCode.toString()) {
                '1'.toInt().toString() -> {
                    isDebugTextOn = !isDebugTextOn
                    println("Swapping debug text $isDebugTextOn")
                }
                '2'.toInt().toString() -> {
                    isNPCPaintOn = !isNPCPaintOn
                    println("Swapping NPC paint $isNPCPaintOn")
                }
                '3'.toInt().toString() -> {
                    isPlayerPaintOn = !isPlayerPaintOn
                    println("Swapping players $isPlayerPaintOn")
                }
                '4'.toInt().toString() -> {
                    isGameObjectOn = !isGameObjectOn
                    println("Swapping gameobjects $isGameObjectOn")
                }
                '5'.toInt().toString() -> {
                    isGroundItemsOn = !isGroundItemsOn
                    println("Swapping gounditems")
                }
                '6'.toInt().toString() -> {
                    isCanWalkDebug = !isCanWalkDebug
                    println("Swapping can walk $isCanWalkDebug")
                }
                '7'.toInt().toString() -> {
                    isProjectileDebug = !isProjectileDebug
                    println("Swapping projectiles $isProjectileDebug")
                }

                '9'.toInt().toString() -> {
                    isInventoryPaintingDebug = !isInventoryPaintingDebug
                    println("Swapping inventory $isInventoryPaintingDebug")
                }
                '0'.toInt().toString() -> {
                    isPaintEquipment = !isPaintEquipment
                    println("Swapping Equipment $isPaintEquipment")
                }
                'Q'.toInt().toString() ->{
                    isBankPaintDebug = !isBankPaintDebug
                    println("Swapping Bank: $isBankPaintDebug")
                }
                'W'.toInt().toString() ->{
                    isClientDebugging = !isClientDebugging
                    println("Swapping Bank: $isClientDebugging")
                }
            }
        }
    }

    override fun keyReleased(e: KeyEvent?) {
    }

    override fun mouseWheelMoved(e: MouseWheelEvent?) {
        if (e != null && e.getWheelRotation() < 0)
        {
            println("mouse wheel Up");
        }
        else
        {
            println("mouse wheel Down");
        }
    }

    override fun mouseReleased(e: MouseEvent?) {
        println("mouseReleased $e")
    }

    override fun mouseEntered(e: MouseEvent?) {
        println("mouseEntered $e")
    }

    override fun mouseClicked(e: MouseEvent?) {
        println("mouseClicked $e")
    }

    override fun mouseExited(e: MouseEvent?) {
        println("mouseExited $e")
    }

    override fun mousePressed(e: MouseEvent?) {
        println("mousePressed $e")
    }


}