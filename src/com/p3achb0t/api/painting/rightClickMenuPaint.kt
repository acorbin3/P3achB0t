package com.p3achb0t.api.painting

import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.Client
import java.awt.Color
import java.awt.Graphics

fun rightClickMenuPaint(g: Graphics, client: com.p3achb0t._runestar_interfaces.Client) {
    try {
        // Look into menu
        val mCount = client.getMenuOptionsCount()
        val heigth = client.getMenuHeight()
        val width = client.getMenuWidth()
        val mX = client.getMenuX()
        val mY = client.getMenuY()
        val mVisible = client.getIsMiniMenuOpen()
        if (mVisible) {
            g.color = Color.YELLOW
            g.drawRect(mX, mY, width, heigth)
            val baseHeight = 18
            val lineHeight = 15
            var yDiff = baseHeight
            for (i in 1..mCount) {

                g.color = Color.BLUE
                g.drawRect(mX - 1, mY + yDiff, width, lineHeight)
                var menuAction = client.getMenuActions()[mCount - i]
                menuAction = Utils.cleanColorText(menuAction)
                var menuOption = client.getMenuTargetNames()[mCount - i]
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