package com.p3achb0t.client.ui

import java.awt.Component
import java.awt.Point
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener


class Mouse(private val component: Component) : MouseListener, MouseMotionListener {
    private var x: Int = 0
    private var y: Int = 0

    /**
     * Mouse cursor current location
     *
     * @return point
     */
    val point: Point
        get() = Point(x, y)

    /**
     * Moves the mouse to the given point and clicks
     *
     * @param x
     * @param y
     * @param left
     */
    fun click(x: Int, y: Int, left: Boolean) {

        moveMouse(x, y)
        Thread.sleep(50, 200)
        pressMouse(x, y, left)
        Thread.sleep(10, 100)
        releaseMouse(x, y, left)
        Thread.sleep(10, 100)
        clickMouse(x, y, left)
    }

    fun pressMouse(x: Int, y: Int, left: Boolean) {
        val me = MouseEvent(component,
                MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, x,
                y, 1, false, if (left) MouseEvent.BUTTON1 else MouseEvent.BUTTON3)
        for (l in component.mouseListeners) {
            if (l !is Mouse) {
                l.mousePressed(me)
            }
        }
    }

    fun click(p: Point, left: Boolean) {
        click(p.x, p.y, left)
    }

    fun click(p: Point) {
        click(p.x, p.y, true)
    }

    fun clickMouse(x: Int, y: Int, left: Boolean) {
        try {

            val me = MouseEvent(component,
                    MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, x,
                    y, 0, false, if (left) MouseEvent.BUTTON1 else MouseEvent.BUTTON3)
            for (l in component.mouseListeners) {
                if (l !is Mouse) {
                    l.mouseClicked(me)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun releaseMouse(x: Int, y: Int, left: Boolean) {
        try {

            val me = MouseEvent(component,
                    MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0, x,
                    y, 0, false, if (left) MouseEvent.BUTTON1 else MouseEvent.BUTTON3)
            for (l in component.mouseListeners) {
                if (l !is Mouse) {
                    l.mouseReleased(me)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * Moves the mouse cursor to the given location
     *
     * @param x
     * @param y
     */
    fun moveMouse(x: Int, y: Int) {
        try {
            val me = MouseEvent(component,
                    MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x,
                    y, 0, false)
            for (l in component.mouseMotionListeners) {
                if (l !is Mouse) {
                    l.mouseMoved(me)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun mouseMoved(e: MouseEvent) {
        x = e.x
        y = e.y
    }

    override fun mouseDragged(e: MouseEvent) {

    }

    override fun mouseClicked(e: MouseEvent) {

    }

    override fun mouseEntered(e: MouseEvent) {

    }

    override fun mouseExited(e: MouseEvent) {

    }

    override fun mousePressed(e: MouseEvent) {

    }

    override fun mouseReleased(e: MouseEvent) {

    }

}