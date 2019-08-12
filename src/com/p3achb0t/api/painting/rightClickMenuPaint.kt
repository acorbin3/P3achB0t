package com.p3achb0t.api.painting

import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.Client
import java.awt.Color
import java.awt.Graphics

fun rightClickMenuPaint(g: Graphics) {
    try {
        // Look into menu
        val mCount = Client.client.getMenuOptionsCount()
        val heigth = Client.client.getMenuHeight()
        val width = Client.client.getMenuWidth()
        val mX = Client.client.getMenuX()
        val mY = Client.client.getMenuY()
        val mVisible = Client.client.getIsMiniMenuOpen()
        if (mVisible) {
            g.color = Color.YELLOW
            g.drawRect(mX, mY, width, heigth)
            val baseHeight = 18
            val lineHeight = 15
            var yDiff = baseHeight
            for (i in 1..mCount) {

                g.color = Color.BLUE
                g.drawRect(mX - 1, mY + yDiff, width, lineHeight)
                var menuAction = Client.client.getMenuActions()[mCount - i]
                menuAction = Utils.cleanColorText(menuAction)
                var menuOption = Client.client.getMenuTargetNames()[mCount - i]
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