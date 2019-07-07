package com.p3achb0t.api.wrappers.interfaces

import com.p3achb0t.Main
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
        return Interact.interact(getInteractPoint(), action)
    }

    suspend fun click(left: Boolean): Boolean {
        return Main.mouse.moveMouse(
            getInteractPoint(),
            click = true,
            clickType = if (left) Mouse.ClickType.Left else Mouse.ClickType.Right
        )
    }

    suspend fun click(): Boolean {
        return Main.mouse.moveMouse(getInteractPoint(), click = true)
    }
}
