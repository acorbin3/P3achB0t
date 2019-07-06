package com.p3achb0t.api.user_inputs

import com.p3achb0t.Main
import kotlinx.coroutines.delay
import java.awt.Point
import java.awt.event.MouseEvent

// This class was replicated based on the mouse movement from:
// https://github.com/cfoust/jane/blob/master/src/automata/tools/input/Mouse.java

class Mouse {
    enum class ClickType {
        Right,
        Left
    }
    //////
    // Config info on how the mouse would operate

    // Interval in pixels at which we will send the mouse events
    private val MIN_DIST_PIXELS = 2

    private val RATE_PIXELS_PER_SEC = 700

    //////

    suspend fun moveMouse(destPoint: Point, click: Boolean = false, clickType: ClickType = ClickType.Left): Boolean {
        if (destPoint == Point(-1, -1)) {
            return false
        }

        val startPoint = if (Main.mouseEvent != null) Main.mouseEvent?.point?.x?.let { _x ->
            Main.mouseEvent?.point?.y?.let { _y ->
                Point(_x, _y)
            }
        } else Point(0, 0)
        val distance = startPoint?.distance(destPoint)
        val timeDurationMS = distance?.div(RATE_PIXELS_PER_SEC)?.times(1000).let { it?.let { it1 -> Math.floor(it1) } }
        val iterations = distance?.div(MIN_DIST_PIXELS)
        val interval = iterations?.let { timeDurationMS?.div(it) }
        val currentPoint = startPoint
        val deltaX = startPoint?.x?.let { iterations?.let { it1 -> destPoint.x.minus(it).div(it1) } }
        val deltaY = startPoint?.y?.let { iterations?.let { it1 -> destPoint.y.minus(it).div(it1) } }
        println("Distance: $distance timeinMS: $timeDurationMS iteration: $iterations interval: $interval deltaX:$deltaX deltaY: $deltaY")
        if (iterations != null) {
            val startX = startPoint.x
            val startY = startPoint.y
            for (i in 1..iterations.toInt() + 1) {
                val xOffset = deltaX?.times(i)?.let { Math.ceil(it) }
                val x = xOffset?.let { startX.plus(it) }
                val yOffset = deltaY?.times(i)?.let { Math.ceil(it) }
                val y = yOffset?.let { startY.plus(it) }
                val mouseMove = x?.toInt()?.let { _x ->
                    //                    println("$x $xOffset $startX    $y $yOffset $startY")
                    y?.toInt()?.let { _y ->
                        currentPoint?.x = _x
                        currentPoint?.y = _y
                        MouseEvent(
                            Main.customCanvas,
                            MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, _x, _y, 0, false
                        )
                    }
                }
                Main.customCanvas?.dispatchEvent(mouseMove)
                interval?.toLong()?.let { delay(it) }
            }
        }
        if (click) {
            val clickMask = if (clickType == ClickType.Right) MouseEvent.BUTTON3_MASK else MouseEvent.BUTTON1_MASK
            val mousePress = currentPoint?.let {
                MouseEvent(
                    Main.customCanvas,
                    MouseEvent.MOUSE_PRESSED,
                    System.currentTimeMillis(),
                    clickMask,
                    it.x,
                    it.y,
                    0,
                    clickType == ClickType.Right
                )

            }
            Main.customCanvas?.dispatchEvent(mousePress)

            // Create a random number 30-70 to delay between clicks
            val delayTime = Math.floor(Math.random() * 40 + 30)
            delay(delayTime.toLong())

            val mouseRelease = currentPoint?.let {
                MouseEvent(
                    Main.customCanvas,
                    MouseEvent.MOUSE_RELEASED,
                    System.currentTimeMillis(),
                    clickMask,
                    it.x,
                    it.y,
                    0,
                    clickType == ClickType.Right
                )

            }
            Main.customCanvas?.dispatchEvent(mouseRelease)
        }
        return true
    }
}