package com.p3achb0t.api.wrappers.interfaces

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

    suspend fun interact(action: String, option: String): Boolean

    suspend fun interact(action: String): Boolean

    suspend fun click(left: Boolean): Boolean

    suspend fun click(): Boolean
}
