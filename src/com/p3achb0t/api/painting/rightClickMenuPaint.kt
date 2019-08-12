package com.p3achb0t.api.painting

import com.p3achb0t.MainApplet
import com.p3achb0t.api.Utils
import java.awt.Color
import java.awt.Graphics

fun rightClickMenuPaint(g: Graphics) {
    try {
        // Look into menu
        val mCount = MainApplet.clientData.getMenuCount()
        val heigth = MainApplet.clientData.getMenuHeight()
        val width = MainApplet.clientData.getMenuWidth()
        val mX = MainApplet.clientData.getMenuX()
        val mY = MainApplet.clientData.getMenuY()
        val mVisible = MainApplet.clientData.getMenuVisible()
        if (mVisible) {
            g.color = Color.YELLOW
            g.drawRect(mX, mY, width, heigth)
            val baseHeight = 18
            val lineHeight = 15
            var yDiff = baseHeight
            for (i in 1..mCount) {

                g.color = Color.BLUE
                g.drawRect(mX - 1, mY + yDiff, width, lineHeight)
                var menuAction = MainApplet.clientData.getMenuActions()[mCount - i]
                menuAction = Utils.cleanColorText(menuAction)
                var menuOption = MainApplet.clientData.getMenuOptions()[mCount - i]
                menuOption = Utils.cleanColorText(menuOption)
                val action = "$menuAction $menuOption"
                g.color = Color.GREEN

                g.drawString(action, mX + width, mY + yDiff + (lineHeight / 2) + 7)
                yDiff += lineHeight
            }
        }
    } catch (e: Exception) {
        println("Error: Menu " + e.message)
    }
}