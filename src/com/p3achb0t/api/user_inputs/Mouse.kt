package com.p3achb0t.api.user_inputs

import com.github.joonasvali.naturalmouse.util.FactoryTemplates
import kotlinx.coroutines.delay
import java.awt.Component
import java.awt.Point
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import kotlin.random.Random

// This class was replicated based on the mouse movement from:
// https://github.com/cfoust/jane/blob/master/src/automata/tools/input/Mouse.java

class Mouse(val component: Component): MouseListener, MouseMotionListener {

    enum class ClickType {
        Right,
        Left
    }


    var mouseEvent: MouseEvent? = null
    val gamerMouse = FactoryTemplates.createFastGamerMotionFactory(this)
    //////
    // Config info on how the mouse would operate

    // Interval in pixels at which we will send the mouse events
    private val MIN_DIST_PIXELS = 2

    private val RATE_PIXELS_PER_SEC = 700

    //////

    suspend fun click(destPoint: Point, clickType: ClickType = ClickType.Left): Boolean {
        return moveMouse(destPoint = destPoint, click = true, clickType = clickType)
    }

    suspend fun moveMouse(destPoint: Point, click: Boolean = false, clickType: ClickType = ClickType.Left): Boolean {
        if (destPoint == Point(-1, -1)) {
            return false
        }

//        val startPoint = if (MainApplet.mouseEvent != null) MainApplet.mouseEvent?.point?.x?.let { _x ->
//            MainApplet.mouseEvent?.point?.y?.let { _y ->
//                Point(_x, _y)
//            }
//        } else {
//            Point(Random.nextInt(Constants.GAME_FIXED_WIDTH), Random.nextInt(Constants.GAME_FIXED_HEIGHT))
//        }
        gamerMouse.move(destPoint.x, destPoint.y)
//        val distance = startPoint?.distance(destPoint)
//        val timeDurationMS = distance?.div(RATE_PIXELS_PER_SEC)?.times(1000).let { it?.let { it1 -> Math.floor(it1) } }
//        val iterations = distance?.div(MIN_DIST_PIXELS)
//        val interval = iterations?.let { timeDurationMS?.div(it) }
//        val currentPoint = startPoint
//        val deltaX = startPoint?.x?.let { iterations?.let { it1 -> destPoint.x.minus(it).div(it1) } }
//        val deltaY = startPoint?.y?.let { iterations?.let { it1 -> destPoint.y.minus(it).div(it1) } }
//        println("Distance: $distance timeinMS: $timeDurationMS iteration: $iterations interval: $interval deltaX:$deltaX deltaY: $deltaY")
//        if (iterations != null) {
//            val startX = startPoint.x
//            val startY = startPoint.y
//            for (i in 1..iterations.toInt() + 1) {
//                val xOffset = deltaX?.times(i)?.let { Math.ceil(it) }
//                val x = xOffset?.let { startX.plus(it) }
//                val yOffset = deltaY?.times(i)?.let { Math.ceil(it) }
//                val y = yOffset?.let { startY.plus(it) }
//                val mouseMove = x?.toInt()?.let { _x ->
//                    //                    println("$x $xOffset $startX    $y $yOffset $startY")
//                    y?.toInt()?.let { _y ->
//                        currentPoint?.x = _x
//                        currentPoint?.y = _y
//                        MouseEvent(
//                            component,
//                            MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, _x, _y, 0, false
//                        )
//                    }
//                }
//                component?.dispatchEvent(mouseMove)
//                interval?.toLong()?.let { delay(it) }
//            }
//        }
        if (click) {
            delay(Random.nextLong(50, 150))
            val clickMask = if (clickType == ClickType.Right) MouseEvent.BUTTON3_MASK else MouseEvent.BUTTON1_MASK
            val mousePress =
                MouseEvent(
                        component,
                    MouseEvent.MOUSE_PRESSED,
                    System.currentTimeMillis(),
                    clickMask,
                    destPoint.x,
                    destPoint.y,
                    0,
                    clickType == ClickType.Right
                )

            component?.dispatchEvent(mousePress)

            // Create a random number 30-70 to delay between clicks
            val delayTime = Math.floor(Math.random() * 40 + 30)
            delay(delayTime.toLong())

            val mouseRelease =
                MouseEvent(
                    component,
                    MouseEvent.MOUSE_RELEASED,
                    System.currentTimeMillis(),
                    clickMask,
                    destPoint.x,
                    destPoint.y,
                    0,
                    clickType == ClickType.Right
                )


            component?.dispatchEvent(mouseRelease)
        }
        return true
    }
    override fun mouseReleased(e: MouseEvent?) {
    }

    override fun mouseEntered(e: MouseEvent?) {
    }

    override fun mouseClicked(e: MouseEvent?) {
    }

    override fun mouseExited(e: MouseEvent?) {
    }

    override fun mousePressed(e: MouseEvent?) {
    }

    override fun mouseMoved(e: MouseEvent?) {
        if (e != null && e.x >= 0 && e.y >= 0)
            mouseEvent = e
    }

    override fun mouseDragged(e: MouseEvent?) {
        if (e != null && e.x >= 0 && e.y >= 0)
            mouseEvent = e
    }
}