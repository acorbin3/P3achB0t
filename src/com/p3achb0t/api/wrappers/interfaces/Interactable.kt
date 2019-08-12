package com.p3achb0t.api.wrappers.interfaces

import com.p3achb0t.MainApplet
import com.p3achb0t.api.Calculations.Companion.midPoint
import com.p3achb0t.api.Constants
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.Interact
import java.awt.Point
import java.awt.Polygon
import kotlin.random.Random

interface Interactable {
    fun getInteractPoint(): Point

    fun getRandomPoint(poly: Polygon): Point {

        if (poly.npoints > 0) {
            var point: Point? = null
            //Pick random point in hull
            while (true) {
                point = Point(
                    Random.nextInt(poly.bounds.x, poly.bounds.width + poly.bounds.x),
                    Random.nextInt(poly.bounds.y, poly.bounds.height + poly.bounds.y)
                )
                if (poly.contains(point)) {
                    return point
                }
            }
        }
        return Point(-1, -1)
    }

    suspend fun interact(action: String, option: String = ""): Boolean {
        return false
    }


    suspend fun interact(action: String): Boolean {
        //Find distance between ineraction point, if distance i > 25, then re compute otherwise interact
        while (true) {
            val desiredPoint = getInteractPoint()
            val startPoint = if (MainApplet.mouseEvent != null) MainApplet.mouseEvent?.point?.x?.let { _x ->
                MainApplet.mouseEvent?.point?.y?.let { _y ->
                    Point(_x, _y)
                }
            } else {
                Point(Random.nextInt(Constants.GAME_FIXED_WIDTH), Constants.GAME_FIXED_HEIGHT)
            }
            val distance = startPoint?.distance(desiredPoint)
            if (distance != null) {
                if (distance < 25) {
                    break
                } else {
                    val midPoint = midPoint(desiredPoint, startPoint)
                    MainApplet.mouse.moveMouse(midPoint, click = false)
                }
            }
        }
        return Interact.interact(getInteractPoint(), action)
    }

    suspend fun hover(click: Boolean = false, clickType: Mouse.ClickType = Mouse.ClickType.Right) {
        MainApplet.mouse.moveMouse(
            getInteractPoint(),
            click = click,
            clickType = clickType
        )
    }

    suspend fun click(left: Boolean): Boolean {
        return MainApplet.mouse.moveMouse(
            getInteractPoint(),
            click = true,
            clickType = if (left) Mouse.ClickType.Left else Mouse.ClickType.Right
        )
    }

    suspend fun clickOnMiniMap(): Boolean

    suspend fun click(): Boolean {
        val point = getInteractPoint()
        return if ((point.x == -1 && point.y == -1) || (point.x == 0 && point.y == 0)) {
            false
        } else {
            MainApplet.mouse.moveMouse(getInteractPoint(), click = true)
        }
    }
}
