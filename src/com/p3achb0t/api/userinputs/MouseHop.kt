package com.p3achb0t.api.userinputs

import com.p3achb0t.api.interfaces.IOHandler
import com.p3achb0t.api.utils.Logging
import com.p3achb0t.api.wrappers.utils.Utils
import kotlinx.coroutines.delay
import java.applet.Applet
import java.awt.Point
import java.awt.event.MouseEvent
import kotlin.math.floor
import kotlin.random.Random

class MouseHop(val scriptManager: IOHandler, val applet: Applet) {

    private val MAX_PIXELS_PER_SEC = 700
    private val MIN_PIXELS_PER_SEC = 500

    suspend fun move(destination: Point){

        val currentPosition = Point(scriptManager.getMouse().getX(),scriptManager.getMouse().getY())
        val distance = currentPosition.distance(destination)
        val timeDelay = floor(distance / Random.nextInt(MIN_PIXELS_PER_SEC, MAX_PIXELS_PER_SEC)).toLong()
        delay(timeDelay)
        setMousePosition(destination)


    }
    suspend fun setMousePosition(point: Point) {
        Utils.sleepUntil({applet != null}, time = 2)
        Utils.sleepUntil({applet.getComponent(0) != null}, time = 2)
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
        Utils.sleepUntil({scriptManager.getMouse() != null}, time = 2)
        if(scriptManager.getMouse() == null){
            Logging.error("Mouse is null")
        }
        scriptManager.getMouse().sendEvent(mouseMove)
    }

}