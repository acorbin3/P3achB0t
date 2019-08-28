package com.p3achb0t.api.wrappers.interfaces

import com.p3achb0t.MainApplet
import com.p3achb0t.api.Calculations.Companion.midPoint
import com.p3achb0t.api.Constants
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.Interact
import kotlinx.coroutines.delay
import java.awt.Point
import java.awt.Polygon
import kotlin.math.abs
import kotlin.random.Random

interface Interactable {
    fun getInteractPoint(): Point
    fun isMouseOverObj(): Boolean

    fun getRandomPoint(poly: Polygon): Point {

        if (poly.npoints > 0) {
            var point: Point? = null
            //Pick random point in hull
            while (true) {
                try {
                    point = Point(
                        Random.nextInt(poly.bounds.x, poly.bounds.width + poly.bounds.x),
                        Random.nextInt(poly.bounds.y, poly.bounds.height + poly.bounds.y)
                    )
                    if (poly.contains(point)) {
                        return point
                    }
                } catch (e: Exception) {
                    return Point(-1, -1)
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
        var desiredPoint = getInteractPoint()
        while (true) {
            val startPoint = if (MainApplet.mouseEvent != null) MainApplet.mouseEvent?.point?.x?.let { _x ->
                MainApplet.mouseEvent?.point?.y?.let { _y ->
                    Point(_x, _y)
                }
            } else {
                Point(Random.nextInt(Constants.GAME_FIXED_WIDTH), Constants.GAME_FIXED_HEIGHT)
            }
//            val distance = startPoint?.distance(desiredPoint)
            MainApplet.mouse.moveMouse(desiredPoint, click = false)
            if(isMouseOverObj()){break}
//            if (distance != null) {
//                if (abs(distance) < 100) {
//                    break
//                } else {
//                    val midPoint = midPoint(desiredPoint, startPoint)
//                    MainApplet.mouse.moveMouse(midPoint, click = false)
//                    delay(Random.nextLong(100, 200))
//                }
//            }
            desiredPoint = getInteractPoint()
        }
        return Interact.interact(desiredPoint, action)
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
