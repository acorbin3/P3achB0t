package com.p3achb0t.api.wrappers

import com.p3achb0t.api.wrappers.utils.Utils
import java.awt.Point
import java.awt.Rectangle
import kotlin.random.Random

class Menu(val client: com.p3achb0t._runestar_interfaces.Client) {
    fun getRect(): Rectangle {
        val mX = client.getMenuX()
        val mY = client.getMenuY()
        val width = client.getMenuWidth()
        val heigth = client.getMenuHeight()
        return Rectangle(mX, mY, width, heigth)
    }

    fun getPointForInteraction(desiredAction: String): Point {
        var point = Point(-1, -1)
        val mCount = client.getMenuOptionsCount()
        val width = client.getMenuWidth()
        val mX = client.getMenuX()
        val mY = client.getMenuY()
        val mVisible = client.getIsMiniMenuOpen()
        if (mVisible) {
            val baseHeight = 18
            val lineHeight = 15
            var yDiff = baseHeight
            for (i in 1..mCount) {
                val action = Utils.cleanColorText(client.getMenuActions()[mCount - i])
                val option = Utils.cleanColorText(client.getMenuTargetNames()[mCount - i])
                val menuItem = "$action $option"

                if (menuItem.toLowerCase().contains(desiredAction.toLowerCase())) {
                    val rec = Rectangle(mX - 1, mY + yDiff, width, lineHeight)
                    while (true) {
                        // Just grab first point and move it to on screen.
                        point = Point(
                                Random.nextInt(rec.bounds.x, rec.bounds.width + rec.bounds.x),
                                Random.nextInt(rec.bounds.y, rec.bounds.height + rec.bounds.y)
                        )
                        if (rec.contains(point))
                            break
                    }
                    println("Looking at menu item : $menuItem")
                    return point
                }
//                    val action = com.p3achb0t.Main.clientData.getMenuActions()[mCount-i] + " " + com.p3achb0t.Main.clientData.getMenuOptions()[mCount-i]
                yDiff += lineHeight
            }
        } else {
            println("Not visible")
        }
        return point
    }

    fun isActionInMenu(action: String): Boolean {
        return getPointForInteraction(action) != Point(-1, -1)
    }

    fun getHoverAction(): String {
        val count = client.getMenuOptionsCount()
        val action = Utils.cleanColorText(client.getMenuActions()[count - 1])
        val option = Utils.cleanColorText(client.getMenuTargetNames()[count - 1])
        return "$action $option"

    }
}