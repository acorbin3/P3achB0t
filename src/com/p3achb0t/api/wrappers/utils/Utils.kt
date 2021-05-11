package com.p3achb0t.api.wrappers.utils

import kotlinx.coroutines.delay
import java.awt.Point
import java.awt.Polygon
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.regex.Pattern
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random


class Utils {
    interface Condition {
        suspend fun accept(): Boolean
    }

    companion object {
        suspend fun waitFor(time: Int, condition: Condition): Boolean {
            var t = Timer(Random.nextLong((time * 1000).toLong(), ((time + 0.5) * 1000).toLong()))
            while (t.isRunning()) {
                if (condition.accept())
                    return true
                delay(Random.nextLong(100, 150))
            }
            return false
        }

        suspend fun sleepUntil(condition: suspend () -> Boolean, time: Int = 5, delayTimeMS: Long = 600): Boolean {
            var istrue = false
            // Convert to milliseconds, then divide by the sleep time to get number of iterations to meet correct duration
            val iterations = time * 1000 / delayTimeMS
            for (i in 1..iterations) {
                if (condition()) {
                    istrue = true
                    break
                }
                delay(delayTimeMS)
            }
            return istrue
        }

        suspend fun waitFor(time: Long, condition: Condition): Boolean {
            var t = Timer(time)
            while (t.isRunning()) {
                if (condition.accept())
                    return true
                delay(Random.nextLong(33, 99))
            }
            return false
        }

        fun cleanColorText(input: String): String {
            return Pattern.compile("<.+?>").matcher(input).replaceAll("")
        }

        fun getElapsedSeconds(timer: Long): Int {
            return (timer / 1000).toInt()
        }


        /**
         * PolygonUtils
         * http://www.shodor.org/~jmorrell/interactivate/org/shodor/util11/PolygonUtils.java
         */
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
}