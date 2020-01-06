package com.p3achb0t.api.wrappers.interfaces

import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.Interact
import java.awt.Point
import java.awt.Polygon
import kotlin.random.Random

abstract class Interactable(var ctx: Context?) {
    abstract fun getInteractPoint(): Point
    abstract fun isMouseOverObj(): Boolean

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


    open suspend fun interact(action: String): Boolean {
        var desiredPoint = getInteractPoint()
        var retryCount = 0
        while (true) {
            ctx?.mouse?.moveMouse(desiredPoint, click = false)
            if (isMouseOverObj() || retryCount == 5) {
                break
            }
            retryCount += 1
            desiredPoint = getInteractPoint()
        }
        return if (ctx?.client != null) ctx?.mouse?.let { Interact(ctx!!).interact(desiredPoint, action) } ?: false else false
    }

    suspend fun hover(click: Boolean = false, clickType: Mouse.ClickType = Mouse.ClickType.Right) {
        ctx?.mouse?.moveMouse(
            getInteractPoint(),
            click = click,
            clickType = clickType

        )
    }

    suspend fun click(left: Boolean): Boolean {
        return ctx?.mouse?.moveMouse(
            getInteractPoint(),
            click = true,
            clickType = if (left) Mouse.ClickType.Left else Mouse.ClickType.Right
        )?: false
    }

    abstract suspend fun clickOnMiniMap(): Boolean

    suspend fun click(): Boolean {
        val point = getInteractPoint()
        return if ((point.x == -1 && point.y == -1) || (point.x == 0 && point.y == 0)) {
            false
        } else {
            ctx?.mouse?.moveMouse(getInteractPoint(), click = true) ?: false
        }
    }
}
