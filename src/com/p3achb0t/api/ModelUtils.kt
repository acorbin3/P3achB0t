package com.p3achb0t.api

import com.p3achb0t._runestar_interfaces.Actor
import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t._runestar_interfaces.EvictingDualNodeHashTable
import com.p3achb0t._runestar_interfaces.Model
import java.awt.Point
import java.awt.Polygon
import java.util.*

data class ObjectPositionInfo(var x: Int, var y: Int, var orientation: Int = 0, val plane: Int = 0)


fun getActorTriangles(actor: Actor?, models: EvictingDualNodeHashTable, modelID: Long, client: Client): ArrayList<Polygon> {
    var polygonList = ArrayList<Polygon>()

    models.getHashTable().getBuckets().iterator().forEach { bucketItem ->
        if (bucketItem != null) {
            var next = bucketItem.getNext()
            while (next != null && next != bucketItem) {
                if (next.getKey() == modelID) {

                    val model = next as Model

                    if (actor != null) {
                        val positionInfo =
                            ObjectPositionInfo(actor.getX(), actor.getY(), actor.getOrientation())
                        polygonList = getTrianglesFromModel(positionInfo, model,client )
                    }
                    break
                }
                next = next.getNext()
            }
        }
    }
    return polygonList
}

fun getTrianglesFromModel(actor: Actor, model: Model, client: Client): ArrayList<Polygon> {
    return getTrianglesFromModel(
            ObjectPositionInfo(actor.getX(), actor.getY(), actor.getOrientation()),
            model,client

    )
}

fun getTrianglesFromModel(
        positionInfo: ObjectPositionInfo,
        model: Model,
        client: Client
): ArrayList<Polygon> {
    val polygonList = ArrayList<Polygon>()
    val locX = positionInfo.x
    val locY = positionInfo.y
    val xPoints = model.getVerticesX().copyOf()
    val yPoints = model.getVerticesY().copyOf()
    val zPoints = model.getVerticesZ().copyOf()
    val indiciesX = model.getIndices1().copyOf()
    val indiciesY = model.getIndices2().copyOf()
    val indiciesZ = model.getIndices3().copyOf()

    val orientation = (positionInfo.orientation).rem(2048)
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


    for (i in 0 until model.getIndicesCount()) {
        try {
            if (i < indiciesX.size && i < indiciesY.size && i < indiciesZ.size) {
                val indicyX = indiciesX[i]
                val indicyY = indiciesY[i]
                val indicyZ = indiciesZ[i]
//                println("i:$i  indicyX:$indicyX indicyY:$indicyY indicyZ:$indicyZ xPoints.size:${xPoints.size} yPoints.size:${yPoints.size} zPoints.size:${zPoints.size} ")
                if (indicyX < xPoints.size && indicyY < xPoints.size && indicyZ < xPoints.size && indicyX >= 0 && indicyY >= 0 && indicyZ >= 0) {
                    val one = Calculations.worldToScreen(
                            locX + xPoints[indiciesX[i]],
                            locY + zPoints[indiciesX[i]], 0 - yPoints[indiciesX[i]],client
                    )
                    val two = Calculations.worldToScreen(
                            locX + xPoints[indiciesY[i]],
                            locY + zPoints[indiciesY[i]], 0 - yPoints[indiciesY[i]],client
                    )
                    val three = Calculations.worldToScreen(
                            locX + xPoints[indiciesZ[i]],
                            locY + zPoints[indiciesZ[i]], 0 - yPoints[indiciesZ[i]],client
                    )
                    if (one.x >= 0 && two.x >= 0 && three.x >= 0
                        && Calculations.isOnscreen(client,one) && Calculations.isOnscreen(client,two ) && Calculations.isOnscreen(client,
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
            }
        } catch (e: Exception) {
            e.stackTrace.iterator().forEach {
                println(it.toString())
            }
        }

    }
    return polygonList
}

fun getConvexHull(actor: Actor?, models: EvictingDualNodeHashTable, modelID: Long, client: Client): Polygon {
    var polygon = Polygon()
    models.getHashTable().getBuckets().iterator().forEach { bucketItem ->
        if (bucketItem != null) {
            var next = bucketItem.getNext()
            while (next != null && next != bucketItem) {
                if (next.getKey() == modelID) {

                    val model = next as Model

                    if (actor != null) {
                        val positionInfo =
                            ObjectPositionInfo(actor.getX(), actor.getY(), actor.getOrientation())
                        polygon = getConvexHullFromModel(positionInfo, model, client)
                    }
                    break
                }
                next = next.getNext()
            }
        }
    }

    return polygon
}

fun getConvexHullFromModel(actor: Actor, model: Model, client: Client): Polygon {
    return getConvexHullFromModel(
            ObjectPositionInfo(actor.getX(), actor.getY(), actor.getOrientation()),
            model,client

    )
}

fun getConvexHullFromModel(
        positionInfo: ObjectPositionInfo,
        model: Model,
        client: com.p3achb0t._runestar_interfaces.Client
): Polygon {
    val pointList = ArrayList<Point>()
    val locX = positionInfo.x
    val locY = positionInfo.y
    val xPoints = model.getVerticesX().copyOf()
    val yPoints = model.getVerticesY().copyOf()
    val zPoints = model.getVerticesZ().copyOf()
    val indiciesX = model.getIndices1().copyOf()
    val indiciesY = model.getIndices2().copyOf()
    val indiciesZ = model.getIndices3().copyOf()

    val orientation = (positionInfo.orientation).rem(2048)
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


    for (i in 0 until model.getIndicesCount()) {
        if (i < indiciesX.size && i < indiciesY.size && i < indiciesZ.size) {
            val indicyX = indiciesX[i]
            val indicyY = indiciesY[i]
            val indicyZ = indiciesZ[i]
            if (indicyX < xPoints.size && indicyY < xPoints.size && indicyZ < xPoints.size && indicyX >= 0 && indicyY >= 0 && indicyZ >= 0) {
                val one = Calculations.worldToScreen(
                        locX + xPoints[indicyX],
                        locY + zPoints[indicyX], 0 - yPoints[indicyX],client
                )
                if (one.x >= 0 && Calculations.isOnscreen(client,one )) pointList.add(one)

                val two = Calculations.worldToScreen(
                        locX + xPoints[indicyY],
                        locY + zPoints[indicyY], 0 - yPoints[indicyY],client
                )
                if (two.x >= 0 && Calculations.isOnscreen(client,two )) pointList.add(two)

//                println("indicyZ:$indicyZ xPoints.size:/=${xPoints.size}")
                val three = Calculations.worldToScreen(
                        locX + xPoints[indicyZ],
                        locY + zPoints[indicyZ], 0 - yPoints[indicyZ],client
                )
                if (three.x >= 0 && Calculations.isOnscreen(client,three )) pointList.add(three)
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