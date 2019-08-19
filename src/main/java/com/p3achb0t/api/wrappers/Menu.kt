package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Utils
import java.awt.Point
import java.awt.Rectangle
import kotlin.random.Random

class Menu {
    companion object {
        fun getRect(): Rectangle {
            val mX = Client.client.getMenuX()
            val mY = Client.client.getMenuY()
            val width = Client.client.getMenuWidth()
            val heigth = Client.client.getMenuHeight()
            return Rectangle(mX, mY, width, heigth)
        }

        fun getPointForInteraction(desiredAction: String): Point {
            var point = Point(-1, -1)
            val mCount = Client.client.getMenuOptionsCount()
            val width = Client.client.getMenuWidth()
            val mX = Client.client.getMenuX()
            val mY = Client.client.getMenuY()
            val mVisible = Client.client.getIsMiniMenuOpen()
            if (mVisible) {
                val baseHeight = 18
                val lineHeight = 15
                var yDiff = baseHeight
                for (i in 1..mCount) {
                    val action = Utils.cleanColorText(Client.client.getMenuActions()[mCount - i])
                    val option = Utils.cleanColorText(Client.client.getMenuTargetNames()[mCount - i])
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
//                    val action = Main.clientData.getMenuActions()[mCount-i] + " " + Main.clientData.getMenuOptions()[mCount-i]
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
            val count = Client.client.getMenuOptionsCount()
            val action = Utils.cleanColorText(Client.client.getMenuActions()[count - 1])
            val option = Utils.cleanColorText(Client.client.getMenuTargetNames()[count - 1])
            return "$action $option"

        }


    }
}