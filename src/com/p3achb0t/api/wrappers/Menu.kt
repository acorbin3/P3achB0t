package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import java.awt.Point
import java.awt.Rectangle
import java.util.regex.Pattern
import kotlin.random.Random

class Menu {
    companion object {
        fun getRect(): Rectangle {
            val mX = Main.clientData.getMenuX()
            val mY = Main.clientData.getMenuY()
            val width = Main.clientData.getMenuWidth()
            val heigth = Main.clientData.getMenuHeight()
            return Rectangle(mX, mY, width, heigth)
        }
        fun getPointForInteraction(action: String): Point {
            var point = Point(-1, -1)
            val mCount = Main.clientData.getMenuCount()
            val width = Main.clientData.getMenuWidth()
            val mX = Main.clientData.getMenuX()
            val mY = Main.clientData.getMenuY()
            val mVisible = Main.clientData.getMenuVisible()
            if (mVisible) {
                val baseHeight = 18
                val lineHeight = 15
                var yDiff = baseHeight
                for (i in 1..mCount) {
                    var input = Main.clientData.getMenuActions()[mCount - i]
                    input = Pattern.compile("<.+?>").matcher(input).replaceAll("")

                    if (input.toLowerCase().contains(action.toLowerCase())) {
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

    }
}