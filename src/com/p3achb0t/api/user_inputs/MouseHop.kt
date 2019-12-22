package com.p3achb0t.api.user_inputs

import com.p3achb0t.interfaces.IScriptManager
import kotlinx.coroutines.delay
import java.applet.Applet
import java.awt.Point
import java.awt.event.MouseEvent
import kotlin.math.floor
import kotlin.random.Random

class MouseHop(val scriptManager: IScriptManager, val applet: Applet) {

    private val MAX_PIXELS_PER_SEC = 700
    private val MIN_PIXELS_PER_SEC = 500

    suspend fun move(destination: Point){

        val currentPosition = Point(scriptManager.getMouse().getX(),scriptManager.getMouse().getY())
        val distance = currentPosition.distance(destination)
        val timeDelay = floor(distance / Random.nextInt(MIN_PIXELS_PER_SEC, MAX_PIXELS_PER_SEC)).toLong()
        delay(timeDelay)
        setMousePosition(destination)


    }
    fun setMousePosition(point: Point) {
        val mouseMove = MouseEvent(
                applet.getComponent(0),
                MouseEvent.MOUSE_MOVED,
                System.currentTimeMillis(),
                0,
                point.x,
                point.y,
                0,
                false
        )
        scriptManager.getMouse().sendEvent(mouseMove)
    }

}