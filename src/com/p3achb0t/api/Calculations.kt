package com.p3achb0t.api

import com.p3achb0t.CustomCanvas
import com.p3achb0t.Main.Data.clientData
import com.p3achb0t.api.Constants.TILE_FLAG_BRIDGE
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.interfaces.Locatable
import java.awt.Point
import java.awt.Polygon
import java.awt.Rectangle
import kotlin.experimental.and


class Calculations {

    companion object {
        val LOCAL_COORD_BITS = 7
        val LOCAL_TILE_SIZE = 1 shl LOCAL_COORD_BITS // 128 - size of a tile in local coordinates
        val LOCAL_HALF_TILE_SIZE = LOCAL_TILE_SIZE / 2

        val SCENE_SIZE = Constants.SCENE_SIZE // in tiles
        var SINE = IntArray(2048)
        var COSINE = IntArray(2048)
        val GAMESCREEN = Rectangle(4, 4, 512, 334)
        val GAMESCREEN_TOP_LEFT = Point(GAMESCREEN.x, GAMESCREEN.y)
        val GAMESCREEN_TOP_RIGHT = Point(GAMESCREEN.width, GAMESCREEN.y)
        val GAMESCREEN_BOTTOM_LEFT = Point(GAMESCREEN.x, GAMESCREEN.height)
        val GAMESCREEN_BOTTOM_RIGHT = Point(GAMESCREEN.width, GAMESCREEN.height)
        val gameScreenPoints = ArrayList<Point>()
        val gameScreenLines = ArrayList<LineF>()

        init {
            for (i in 0 until SINE.size) {
                SINE[i] = (65536.0 * Math.sin(i.toDouble() * 0.0030679615)).toInt()
                COSINE[i] = (65536.0 * Math.cos(i.toDouble() * 0.0030679615)).toInt()
            }
            gameScreenPoints.add(GAMESCREEN_TOP_LEFT)
            gameScreenPoints.add(GAMESCREEN_TOP_RIGHT)
            gameScreenPoints.add(GAMESCREEN_BOTTOM_RIGHT)
            gameScreenPoints.add(GAMESCREEN_BOTTOM_LEFT)
            gameScreenLines.add(LineF(GAMESCREEN_TOP_LEFT, GAMESCREEN_TOP_RIGHT))
            gameScreenLines.add(LineF(GAMESCREEN_TOP_RIGHT, GAMESCREEN_BOTTOM_RIGHT))
            gameScreenLines.add(LineF(GAMESCREEN_BOTTOM_RIGHT, GAMESCREEN_BOTTOM_LEFT))
            gameScreenLines.add(LineF(GAMESCREEN_BOTTOM_LEFT, GAMESCREEN_TOP_LEFT))
        }

        fun getTileHeight(plane: Int, x: Int, y: Int): Int {
            val xx = x shr 7
            val yy = y shr 7
            if (xx < 0 || yy < 0 || xx > 103 || yy > 103) {
                return 0
            }
            val tileHeights = clientData.getTileHeights()
            val aa = tileHeights[plane][xx][yy] * (128 - (x and 0x7F)) + tileHeights[plane][xx + 1][yy] * (x and 0x7F) shr 7

            val ab =
                tileHeights[plane][xx][yy + 1] * (128 - (x and 0x7F)) + tileHeights[plane][xx + 1][yy + 1] * (x and 0x7F) shr 7
            return aa * (128 - (y and 0x7F)) + ab * (y and 0x7F) shr 7
        }

        /**
         * @param regionX
         * @param regionY
         * @param height
         * @return Point : Convert from tile to point on screen
         */
        fun worldToScreen(regionX: Int, regionY: Int, height: Int): Point {
            var x = regionX
            var y = regionY
            if (x < 128 || y < 128 || x > 13056 || y > 13056) {
                return Point(-1, -1)
            }
            var z = getTileHeight(clientData.getPlane(), x, y) - height
            x -= clientData.getCameraX()
            z -= clientData.getCameraZ()
            y -= clientData.getCameraY()

            val yaw = clientData.getCameraYaw()
            val pitch = clientData.getCameraPitch()

            val pitch_sin = SINE[pitch]
            val pitch_cos = COSINE[pitch]
            val yaw_sin = SINE[yaw]
            val yaw_cos = COSINE[yaw]

            var _angle = y * yaw_sin + x * yaw_cos shr 16

            y = y * yaw_cos - x * yaw_sin shr 16
            x = _angle
            _angle = z * pitch_cos - y * pitch_sin shr 16
            y = z * pitch_sin + y * pitch_cos shr 16
            z = _angle


            return if (y >= 50) {
                val screenX = x * clientData.getZoomExact() / y + CustomCanvas.dimension.width / 2
                val screenY = z * clientData.getZoomExact() / y + CustomCanvas.dimension.height / 2
                Point(screenX, screenY)
            } else Point(-1, -1)
        }


        //TODO - Recalculate GameScreen for resize mode
        fun isOnscreen(point: Point): Boolean {
            return GAMESCREEN.contains(point)
        }

        fun isOnscreen(x: Int, y: Int): Boolean {
            return GAMESCREEN.contains(Point(x, y))
        }

        fun isOnscreen(rectangle: Rectangle): Boolean {
            return GAMESCREEN.intersects(rectangle)
        }

//        fun worldToMap(regionX: Int, regionY: Int): Point {
//            clientData.getMap
//            val mapScale = Reflection.value("Client#getMapScale()", null) as Int
//            val mapOffset = Reflection.value("Client#getMapOffset()", null) as Int
//            val angle = clientData.getMapAngle() + mapScale and 0x7FF
//            val j = regionX * regionX + regionY * regionY
//            if (j > 6400)
//                return Point(-1, -1)
//
//            val sin = SINE[angle] * 256 / (mapOffset + 256)
//            val cos = COSINE[angle] * 256 / (mapOffset + 256)
//
//            val xMap = regionY * sin + regionX * cos shr 16
//            val yMap = regionY * cos - regionX * sin shr 16
//
//            return Point(644 + xMap, 80 - yMap)
//        }

        private fun getHeight(localX: Int, localY: Int, plane: Int): Int {
            val sceneX = localX shr LOCAL_COORD_BITS
            val sceneY = localY shr LOCAL_COORD_BITS
            if (sceneX >= 0 && sceneY >= 0 && sceneX < SCENE_SIZE && sceneY < SCENE_SIZE) {
                val tileHeights = clientData.getTileHeights()

                val x = localX.and(LOCAL_TILE_SIZE - 1)
                val y = localY.and(LOCAL_TILE_SIZE - 1)
                val var8 =
                    (x * tileHeights[plane][sceneX + 1][sceneY] + (LOCAL_TILE_SIZE - x) * tileHeights[plane][sceneX][sceneY]) shr LOCAL_COORD_BITS
                val var9 =
                    (tileHeights[plane][sceneX][sceneY + 1] * (LOCAL_TILE_SIZE - x) + x * tileHeights[plane][sceneX + 1][sceneY + 1]) shr LOCAL_COORD_BITS
                return ((LOCAL_TILE_SIZE - y) * var8 + y * var9) shr LOCAL_COORD_BITS + 168
            }

            return 0
        }

        class LineF(var s: Point, var e: Point)

        fun findIntersection(l1: LineF, l2: LineF): Point {
            val a1 = l1.e.y - l1.s.y
            val b1 = l1.s.x - l1.e.x
            val c1 = a1 * l1.s.x + b1 * l1.s.y

            val a2 = l2.e.y - l2.s.y
            val b2 = l2.s.x - l2.e.x
            val c2 = a2 * l2.s.x + b2 * l2.s.y

            val delta = a1 * b2 - a2 * b1
            if (delta == 0) {
                return Point(-1, -1)
            }
            // If lines are parallel, intersection point will contain infinite values
            return Point((b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta)
        }

        /**
         * Returns a polygon representing an area. TODO- Make it so the points are all on the screen
         *
         * @param client the game client
         * @param localLocation the center location of the AoE
         * @param size the size of the area (ie. 3x3 AoE evaluates to size 3)
         * @return a polygon representing the tiles in the area
         */
        fun getCanvasTileAreaPoly(localX: Int, localY: Int, size: Int = 1): Polygon {
            val plane = clientData.getPlane()

            val swX = localX - size * LOCAL_TILE_SIZE / 2
            val swY = localY - size * LOCAL_TILE_SIZE / 2

            val neX = localX + size * LOCAL_TILE_SIZE / 2
            val neY = localY + size * LOCAL_TILE_SIZE / 2

            val tileSettings = clientData.getTileSettings()

            val sceneX = localX.ushr(LOCAL_COORD_BITS)
            val sceneY = localY.ushr(LOCAL_COORD_BITS)

            if (sceneX < 0 || sceneY < 0 || sceneX >= SCENE_SIZE || sceneY >= SCENE_SIZE) {
                return Polygon()
            }

            var tilePlane = plane
            if (plane < Constants.MAX_Z - 1 && tileSettings[1][sceneX][sceneY].and(TILE_FLAG_BRIDGE.toByte()).toInt() == TILE_FLAG_BRIDGE) {
                tilePlane = plane + 1
            }
            if (tilePlane > 0)
                tilePlane -= 1

            val swHeight = getHeight(swX, swY, tilePlane)
            val nwHeight = getHeight(neX, swY, tilePlane)
            val neHeight = getHeight(neX, neY, tilePlane)
            val seHeight = getHeight(swX, neY, tilePlane)

            val p1 = worldToScreen(swX, swY, swHeight)
            val p2 = worldToScreen(neX, swY, nwHeight)
            val p3 = worldToScreen(neX, neY, neHeight)
            val p4 = worldToScreen(swX, neY, seHeight)

            //All off screen
            if (!isOnscreen(p1) && !isOnscreen(p2) && !isOnscreen(p3) && !isOnscreen(p4)) {
                return Polygon()
            }

            val poly = Polygon()
            poly.addPoint(p1.getX().toInt(), p1.getY().toInt())
            poly.addPoint(p2.getX().toInt(), p2.getY().toInt())
            poly.addPoint(p3.getX().toInt(), p3.getY().toInt())
            poly.addPoint(p4.getX().toInt(), p4.getY().toInt())

            return poly
        }

        fun distanceBetween(a: Tile, b: Tile): Int {
            return distanceBetween(a.x, a.y, b.x, b.y)
        }

        fun distanceBetween(a: Point, b: Point): Int {
            return distanceBetween(a.x, a.y, b.x, b.y)
        }

        fun distanceBetween(x: Int, y: Int, x1: Int, y1: Int): Int {
            return Math.sqrt(Math.pow((x1 - x).toDouble(), 2.0) + Math.pow((y1 - y).toDouble(), 2.0)).toInt()
        }

        /**
         * @param a tile
         * @return current distance between player and specific tile
         */
        fun distanceTo(a: Tile): Int {
            val loc = com.p3achb0t.api.wrappers.Players.getLocal().getLocation()
            return distanceBetween(a.x, a.y, loc.x, loc.y)
        }

        fun distanceTo(a: Locatable): Int {
            val loc = com.p3achb0t.api.wrappers.Players.getLocal().getLocation()
            return distanceBetween(a.getLocation().x, a.getLocation().y, loc.x, loc.y)
        }
    }
}