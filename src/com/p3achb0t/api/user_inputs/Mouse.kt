package com.p3achb0t.api.user_inputs

import com.naturalmouse.api.MouseMotionFactory
import com.naturalmouse.custom.RuneScapeFactoryTemplates
import com.p3achb0t.client.interfaces.io.Mouse
import com.p3achb0t.interfaces.IScriptManager
import kotlinx.coroutines.delay
import java.applet.Applet
import java.awt.Component
import java.awt.Point
import java.awt.event.MouseEvent
import kotlin.random.Random

// This class was replicated based on the mouse movement from:
// https://github.com/cfoust/jane/blob/master/src/automata/tools/input/Mouse.java

class Mouse(obj: Any) {

    private val component: Component = (obj as Applet).getComponent(0)
    private val mouseMotionFactory: MouseMotionFactory = RuneScapeFactoryTemplates.createAverageComputerUserMotionFactory(obj)
    private val ioMouse: Mouse = (obj as IScriptManager).getMouse()

    fun getX() : Int {
        return ioMouse.getX()
    }


    fun getY() : Int {
        return ioMouse.getY()
    }

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

    suspend fun click(destPoint: Point, clickType: ClickType = ClickType.Left): Boolean {
        return moveMouse(destPoint = destPoint, click = true, clickType = clickType)
    }

    suspend fun moveMouse(destPoint: Point, click: Boolean = false, clickType: ClickType = ClickType.Left): Boolean {
        if (destPoint == Point(-1, -1)) {
            return false
        }

        mouseMotionFactory.move(destPoint.x, destPoint.y)

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

            ioMouse.sendEvent(mousePress)

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
            ioMouse.sendEvent(mouseRelease)
        }
        return true
    }
}