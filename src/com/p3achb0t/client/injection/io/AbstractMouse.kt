package com.p3achb0t.client.injection.io

import com.p3achb0t.api.interfaces.Mouse
import java.awt.event.*


abstract class AbstractMouse : MouseListener, MouseMotionListener, MouseWheelListener, Mouse {

    private var inputBlocked = false

    private var isPressed = false



    override fun inputBlocked(value: Boolean) {
        inputBlocked = value
    }

    override fun inputBlocked(): Boolean {
        return inputBlocked
    }

    abstract override fun getX(): Int

    abstract override fun getY(): Int

    abstract fun _mouseClicked(e: MouseEvent)

    abstract fun _mouseDragged(e: MouseEvent)

    abstract fun _mouseEntered(e: MouseEvent)

    abstract fun _mouseExited(e: MouseEvent)

    abstract fun _mouseMoved(e: MouseEvent)

    abstract fun _mousePressed(e: MouseEvent)

    abstract fun _mouseReleased(e: MouseEvent)

    abstract fun _mouseWheelMoved(e: MouseWheelEvent)

    override fun mouseClicked(e: MouseEvent) {
        if (!inputBlocked)
            _mouseClicked(e)
        e.consume()
    }

    override fun mousePressed(e: MouseEvent) {
        isPressed = true
        if (!inputBlocked)
            _mousePressed(e)
        e.consume()
    }

    override fun mouseReleased(e: MouseEvent) {
        isPressed = false
        if (!inputBlocked)
            _mouseReleased(e)
        e.consume()
    }

    override fun mouseEntered(e: MouseEvent) {
        if (!inputBlocked)
            _mouseEntered(e)
        e.consume()
    }

    override fun mouseExited(e: MouseEvent) {
        if (!inputBlocked)
            _mouseExited(e)
        e.consume()
    }

    override fun mouseDragged(e: MouseEvent) {
        if (!inputBlocked)
            _mouseDragged(e)
        e.consume()
    }

    override fun mouseMoved(e: MouseEvent) {
        //System.out.println(e);
        if (!inputBlocked)
            _mouseMoved(e)
        e.consume()
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        println("Scroll $e")
        if (!inputBlocked)
            _mouseWheelMoved(e)
        e.consume()
    }

    override fun sendEvent(e: MouseEvent) {
        try {
            when (e.id) {
                MouseEvent.MOUSE_CLICKED -> _mouseClicked(e)
                MouseEvent.MOUSE_DRAGGED -> _mouseDragged(e)
                MouseEvent.MOUSE_ENTERED -> _mouseEntered(e)
                MouseEvent.MOUSE_EXITED -> _mouseExited(e)
                MouseEvent.MOUSE_MOVED -> _mouseMoved(e)
                MouseEvent.MOUSE_PRESSED -> _mousePressed(e)
                MouseEvent.MOUSE_RELEASED -> _mouseReleased(e)
                MouseEvent.MOUSE_WHEEL -> _mouseWheelMoved(e as MouseWheelEvent)
            }
            e.consume()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun sendScrollEvent(e: MouseWheelEvent) {
        try{
            _mouseWheelMoved(e)
            e.consume()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

}