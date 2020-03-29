package com.p3achb0t.api.wrappers.interfaces

import com.p3achb0t.api.Context
import com.p3achb0t.api.userinputs.Mouse
import com.p3achb0t.api.wrappers.*
import java.awt.Point
import java.awt.Polygon
import kotlin.math.abs
import kotlin.math.roundToInt

abstract class Interactable(var ctx: Context?) {
    abstract fun getInteractPoint(): Point
    abstract fun isMouseOverObj(): Boolean

//    fun getRandomPoint(poly: Polygon): Point {
//
//        if (poly.npoints > 0) {
//            var point: Point? = null
//            //Pick random point in hull
//            while (true) {
//                try {
//                    point = Point(
//
//                        Random.nextInt(poly.bounds.x, poly.bounds.width + poly.bounds.x),
//                        Random.nextInt(poly.bounds.y, poly.bounds.height + poly.bounds.y)
//                    )
//                    if (poly.contains(point)) {
//                        return point
//                    }
//                } catch (e: Exception) {
//                    return Point(-1, -1)
//                }
//            }
//        }
//        return Point(-1, -1)
//    }

    /**
     * PolygonUtils
     * http://www.shodor.org/~jmorrell/interactivate/org/shodor/util11/PolygonUtils.java
     */
    private object PolygonUtils {
        /**
         * Finds the centroid of a polygon with integer verticies.
         *
         * @param pg The polygon to find the centroid of.
         * @return The centroid of the polygon.
         */

        fun getCenter(pg: Polygon): Point {

            val N = pg.npoints
            val polygon = Array(N) { _ -> Point() }

            for (q in 0 until N) {
                polygon[q] = Point(pg.xpoints[q], pg.ypoints[q])
            }

            var cx = 0.0
            var cy = 0.0
            val A = getArea(polygon, N)
            var i: Int = 0
            var j: Int

            var factor: Double
            while (i < N) {
                j = ((i + 1) % N)
                factor = ((polygon[i].x) * polygon[j].y - (polygon[j].x) * (polygon[i].y)).toDouble()
                cx += ((polygon[i].x) + (polygon[j].x)) * factor
                cy += ((polygon[i].y) + (polygon[j].y)) * factor
                i++
            }
            factor = 1.0 / (6.0 * A)
            cx *= factor
            cy *= factor
            return Point(abs(cx.roundToInt()), abs(cy.roundToInt()))
        }

        /**
         * Computes the area of any two-dimensional polygon.
         *
         * @param polygon The polygon to compute the area of input as an array of points
         * @param N       The number of points the polygon has, first and last point
         * inclusive.
         * @return The area of the polygon.
         */
        fun getArea(polygon: Array<Point>, N: Int): Double {
            var i: Int = 0
            var j: Int
            var area = 0.0

            while (i < N) {
                j = (i + 1) % N
                area += polygon[i].x * polygon[j].y
                area -= polygon[i].y * polygon[j].x
                i++
            }

            area /= 2.0
            return abs(area)
        }
    }

    fun getRandomPoint(poly: Polygon): Point {

        if (poly.npoints > 0) {
            var point: Point? = null
            //Pick random point in hull
            while (true) {
                try {
                    point = Point(

                            PolygonUtils.getCenter(poly)
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
            if (isMouseOverObj() || retryCount == 1) {
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

//    suspend fun click(left: Boolean): Boolean {
//        return ctx?.mouse?.moveMouse(
//            getInteractPoint(),
//            click = true,
//            clickType = if (left) Mouse.ClickType.Left else Mouse.ClickType.Right
//        )?: false
//    }

    abstract suspend fun clickOnMiniMap(): Boolean

    suspend fun click(): Boolean {
        val point = getInteractPoint()
        return if ((point.x == -1 && point.y == -1) || (point.x == 0 && point.y == 0)) {
            false
        } else {
            ctx?.mouse?.moveMouse(getInteractPoint(), click = true) ?: false
        }
    }

    suspend fun clickGroundObject(groundItem: GroundItem): Boolean {
        val point = getInteractPoint()
        return if ((point.x == -1 && point.y == -1) || (point.x == 0 && point.y == 0)) {
            false
        } else {
            ctx?.mouse?.moveMouseGroundItem(groundItem, getInteractPoint(), click = true) ?: false
        }
    }

    suspend fun clickNPC(npc: NPC): Boolean {
        val point = getInteractPoint()
        return if ((point.x == -1 && point.y == -1) || (point.x == 0 && point.y == 0)) {
            false
        } else {
            ctx?.mouse?.moveMouseNPC(npc, getInteractPoint(), click = true) ?: false
        }
    }

    suspend fun clickObject(obj: GameObject): Boolean {
        val point = getInteractPoint()
        return if ((point.x == -1 && point.y == -1) || (point.x == 0 && point.y == 0)) {
            false
        } else {
            ctx?.mouse?.moveMouseObject(obj, getInteractPoint(), click = true) ?: false
        }
    }
}
