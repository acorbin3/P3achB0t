package com.p3achb0t.scripts.paint_debug

import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.scripts.paint_debug.PaintDebug.Companion.drawRect
import java.awt.Color
import java.awt.Graphics

fun widgetBlockingPaint(g: Graphics) {
    try {
        g.color = Color.red
        drawRect(
            g,
            Calculations.chatBoxDimensions
        )
        drawRect(
            g,
            Calculations.inventoryBarBottomDimensions
        )
        drawRect(
            g,
            Calculations.inventoryDimensions
        )
        drawRect(
            g,
            Calculations.inventoryBarTopDimensions
        )
        drawRect(
            g,
            Calculations.miniMapDimensions
        )
    } catch (e: Exception) {
        println("Error: Bounds " + e.toString())
    }
}