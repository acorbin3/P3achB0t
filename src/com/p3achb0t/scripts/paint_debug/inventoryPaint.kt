package com.p3achb0t.scripts.paint_debug

import com.p3achb0t.api.Context
import java.awt.Color
import java.awt.Graphics

fun inventoryPaint(g: Graphics, ctx: Context) {
    try {

        val items = ctx.inventory.getAll()
        // Look at inventory
//            if (items.size > 0) {
//                items.forEach {
//                    g.color = Color.YELLOW
//                    g.drawString("${it.id}", it.getBasePoint().x, it.getBasePoint().y)
//                    g.color = Color.GREEN
//                    g.drawString("${it.stackSize}", it.getBasePoint().x + 10, it.getBasePoint().y + 10)
//
//                    g.color = Color.RED
//                    g.drawRect(it.area.x, it.area.y, it.area.width, it.area.height)
//                }
//            }
    } catch (e: Exception) {
        println("Error: Inventory " + e.message)
    }
}