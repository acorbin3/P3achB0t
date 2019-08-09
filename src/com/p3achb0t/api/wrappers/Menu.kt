package com.p3achb0t.api.wrappers

import com.p3achb0t.MainApplet
import com.p3achb0t.api.Utils
import java.awt.Point
import java.awt.Rectangle
import kotlin.random.Random

class Menu {
    companion object {
        fun getRect(): Rectangle {
            val mX = MainApplet.clientData.getMenuX()
            val mY = MainApplet.clientData.getMenuY()
            val width = MainApplet.clientData.getMenuWidth()
            val heigth = MainApplet.clientData.getMenuHeight()
            return Rectangle(mX, mY, width, heigth)
        }
        fun getPointForInteraction(action: String): Point {
            var point = Point(-1, -1)
            val mCount = MainApplet.clientData.getMenuCount()
            val width = MainApplet.clientData.getMenuWidth()
            val mX = MainApplet.clientData.getMenuX()
            val mY = MainApplet.clientData.getMenuY()
            val mVisible = MainApplet.clientData.getMenuVisible()
            if (mVisible) {
                val baseHeight = 18
                val lineHeight = 15
                var yDiff = baseHeight
                for (i in 1..mCount) {
                    val action = Utils.cleanColorText(MainApplet.clientData.getMenuActions()[mCount - i])
                    val option = Utils.cleanColorText(MainApplet.clientData.getMenuOptions()[mCount - i])
                    val menuItem = "$action $option"

                    if (menuItem.toLowerCase().contains(action.toLowerCase())) {
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
            val count = MainApplet.clientData.getMenuCount()
            val action = Utils.cleanColorText(MainApplet.clientData.getMenuActions()[count - 1])
            val option = Utils.cleanColorText(MainApplet.clientData.getMenuOptions()[count - 1])
            return "$action $option"

        }


    }
}