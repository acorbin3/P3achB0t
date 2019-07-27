package com.p3achb0t.api.painting

import com.p3achb0t.Main
import com.p3achb0t.api.Utils
import java.awt.Color
import java.awt.Graphics

fun rightClickMenuPaint(g: Graphics) {
    try {
        // Look into menu
        val mCount = Main.clientData.getMenuCount()
        val heigth = Main.clientData.getMenuHeight()
        val width = Main.clientData.getMenuWidth()
        val mX = Main.clientData.getMenuX()
        val mY = Main.clientData.getMenuY()
        val mVisible = Main.clientData.getMenuVisible()
        if (mVisible) {
            g.color = Color.YELLOW
            g.drawRect(mX, mY, width, heigth)
            val baseHeight = 18
            val lineHeight = 15
            var yDiff = baseHeight
            for (i in 1..mCount) {

                g.color = Color.BLUE
                g.drawRect(mX - 1, mY + yDiff, width, lineHeight)
                var menuAction = Main.clientData.getMenuActions()[mCount - i]
                menuAction = Utils.cleanColorText(menuAction)
                var menuOption = Main.clientData.getMenuOptions()[mCount - i]
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