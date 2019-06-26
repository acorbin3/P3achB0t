package com.p3achb0t.api

import com.p3achb0t.hook_interfaces.Actor
import com.p3achb0t.hook_interfaces.Cache
import com.p3achb0t.hook_interfaces.Model
import java.awt.Point
import java.awt.Polygon
import java.util.*


fun getActorTriangles(actor: Actor?, models: Cache, modelID: Long): ArrayList<Polygon> {
    var polygonList = ArrayList<Polygon>()
    models.getHashTable().getBuckets().iterator().forEach { bucketItem ->
        if (bucketItem != null) {
            var next = bucketItem.getNext()
            while (next != null) {
                if (next.getId() == modelID) {

                    val model = next as Model

                    if (actor != null) {
                        polygonList = getTrianglesFromModel(actor, model)
                    }
                    break
                }
                next = next.getNext()
            }
        }
    }
    return polygonList
}

fun getTrianglesFromModel(
    actor: Actor,
    model: Model
): ArrayList<Polygon> {
    val polygonList = ArrayList<Polygon>()
    val locX = actor.getLocalX()
    val locY = actor.getLocalY()
    val xPoints = model.getVerticesX().copyOf()
    val yPoints = model.getVerticesY().copyOf()
    val zPoints = model.getVerticesZ().copyOf()
    val indiciesX = model.getIndicesX().copyOf()
    val indiciesY = model.getIndicesY().copyOf()
    val indiciesZ = model.getIndicesZ().copyOf()

    val orientation = (actor.getOrientation()).rem(2048)
    if (orientation != 0) {
        val sin = Calculations.SINE[orientation]
        val cos = Calculations.COSINE[orientation]
        for (i in 0 until xPoints.size) {
            val oldX = xPoints[i]
            val oldZ = zPoints[i]
            xPoints[i] = (oldX * cos + oldZ * sin).shr(16)
            zPoints[i] = (oldZ * cos - oldX * sin).shr(16)
        }
    }


    for (i in 0 until model.getIndicesLength()) {
        val one = Calculations.worldToScreen(
            locX + xPoints[indiciesX[i]],
            locY + zPoints[indiciesX[i]], 0 - yPoints[indiciesX[i]]
        )
        val two = Calculations.worldToScreen(
            locX + xPoints[indiciesY[i]],
            locY + zPoints[indiciesY[i]], 0 - yPoints[indiciesY[i]]
        )
        val three = Calculations.worldToScreen(
            locX + xPoints[indiciesZ[i]],
            locY + zPoints[indiciesZ[i]], 0 - yPoints[indiciesZ[i]]
        )
        if (one.x >= 0 && two.x >= 0 && three.x >= 0
            && Calculations.isOnscreen(one) && Calculations.isOnscreen(two) && Calculations.isOnscreen(
                three
            )
        ) {
            polygonList.add(
                Polygon(
                    intArrayOf(one.x, two.x, three.x),
                    intArrayOf(one.y, two.y, three.y),
                    3
                )
            )
        }

    }
    return polygonList
}

fun getConvexHull(actor: Actor?, models: Cache, modelID: Long): Polygon {
    val pointList = ArrayList<Point>()
    models.getHashTable().getBuckets().iterator().forEach { bucketItem ->
        if (bucketItem != null) {
            val next = bucketItem.getNext()
            if (next.getId() == modelID) {

                val model = next as Model

                if (actor != null) {

                    val locX = actor.getLocalX()
                    val locY = actor.getLocalY()
                    val xPoints = model.getVerticesX().copyOf()
                    val yPoints = model.getVerticesY().copyOf()
                    val zPoints = model.getVerticesZ().copyOf()
                    val indiciesX = model.getIndicesX().copyOf()
                    val indiciesY = model.getIndicesY().copyOf()
                    val indiciesZ = model.getIndicesZ().copyOf()

                    val orientation = (actor.getOrientation()).rem(2048)
                    if (orientation != 0) {
                        val sin = Calculations.SINE[orientation]
                        val cos = Calculations.COSINE[orientation]
                        for (i in 0 until xPoints.size) {
                            val oldX = xPoints[i]
                            val oldZ = zPoints[i]
                            xPoints[i] = (oldX * cos + oldZ * sin).shr(16)
                            zPoints[i] = (oldZ * cos - oldX * sin).shr(16)
                        }
                    }


                    for (i in 0 until model.getIndicesLength()) {
                        val one = Calculations.worldToScreen(
                            locX + xPoints[indiciesX[i]],
                            locY + zPoints[indiciesX[i]], 0 - yPoints[indiciesX[i]]
                        )
                        if (one.x >= 0 && Calculations.isOnscreen(one)) pointList.add(one)

                        val two = Calculations.worldToScreen(
                            locX + xPoints[indiciesY[i]],
                            locY + zPoints[indiciesY[i]], 0 - yPoints[indiciesY[i]]
                        )
                        if (two.x >= 0 && Calculations.isOnscreen(two)) pointList.add(two)

                        val three = Calculations.worldToScreen(
                            locX + xPoints[indiciesZ[i]],
                            locY + zPoints[indiciesZ[i]], 0 - yPoints[indiciesZ[i]]
                        )
                        if (three.x >= 0 && Calculations.isOnscreen(three)) pointList.add(three)
                    }
                }
            }
        }
    }
    val points = calculateConvexHull(pointList)
    val polygon = Polygon()
    points?.forEach {
        polygon.addPoint(it.x, it.y)
    }
    return polygon
}

/**
 * Computes and returns the convex hull of the passed points.
 * <p>
 * The size of the list must be at least 4, otherwise this method will
 * return null.
 *
 * @param points list of points
 * @return list containing the points part of the convex hull
 */
fun calculateConvexHull(points: List<Point>): List<Point>? {
    if (points.size < 3) {
        return null
    }

    val convexHull = ArrayList<Point>()

    // find the left most point
    val left = findLeftMost(points)

    // current point we are on
    var current = left

    do {
        current?.let { convexHull.add(it) }
        assert(convexHull.size <= points.size) { "hull has more points than graph" }
        if (convexHull.size > points.size) {
            // Just to make sure we never somehow get stuck in this loop
            return null
        }

        // the next point - all points are to the right of the
        // line between current and next
        var next: Point? = null

        for (p in points) {
            if (next == null) {
                next = p
                continue
            }

            val cp = current?.let { crossProduct(it, p, next!!) }
            if (cp != null && current != null) {
                if (cp > 0 || cp == 0L && current.distance(p) > current.distance(next)) {
                    next = p
                }
            }
        }

        // Points can be null if they are behind or very close to the camera.
        if (next == null) {
            return null
        }

        current = next
    } while (current !== left)

    return convexHull
}

private fun findLeftMost(points: List<Point>): Point? {
    var left: Point? = null

    for (p in points) {
        if (left == null || p.getX() < left.getX()) {
            left = p
        } else if (p.getX().equals(left.getX()) && p.getY() < left.getY()) {
            left = p
        }
    }

    return left
}

private fun crossProduct(p: Point, q: Point, r: Point): Long {
    return ((q.getY() - p.getY()).toLong() * (r.getX() - q.getX()) - (q.getX() - p.getX()).toLong() * (r.getY() - q.getY())).toLong()
}