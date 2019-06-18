package com.p3achb0t.api

import com.p3achb0t.Main.Data.clientData
import com.p3achb0t.api.Constants.TILE_FLAG_BRIDGE
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
        init {
            for (i in 0 until SINE.size) {
                SINE[i] = (65536.0 * Math.sin(i.toDouble() * 0.0030679615)).toInt()
                COSINE[i] = (65536.0 * Math.cos(i.toDouble() * 0.0030679615)).toInt()
            }
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
            var regionX = regionX
            var regionY = regionY
            if (regionX < 128 || regionY < 128 || regionX > 13056 || regionY > 13056) {
                return Point(-1, -1)
            }
            var z = getTileHeight(clientData.getPlane(), regionX, regionY) - height
            regionX -= clientData.getCameraX()
            z -= clientData.getCameraZ()
            regionY -= clientData.getCameraY()

            val yaw = clientData.getCameraYaw()
            val pitch = clientData.getCameraPitch()

            val pitch_sin = SINE[pitch]
            val pitch_cos = COSINE[pitch]
            val yaw_sin = SINE[yaw]
            val yaw_cos = COSINE[yaw]

            var _angle = regionY * yaw_sin + regionX * yaw_cos shr 16

            regionY = regionY * yaw_cos - regionX * yaw_sin shr 16
            regionX = _angle
            _angle = z * pitch_cos - regionY * pitch_sin shr 16
            regionY = z * pitch_sin + regionY * pitch_cos shr 16


            return if (regionY >= 50) {
                Point(258 + (regionX shl 9) / regionY, (_angle shl 9) / regionY + 170)
            } else Point(-1, -1)
        }

        fun isOnscreen(point: Point): Boolean {
            return GAMESCREEN.contains(point)
        }

        fun isOnscreen(x: Int, y: Int): Boolean {
            return GAMESCREEN.contains(Point(x, y))
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


        /**
         * Returns a polygon representing an area.
         *
         * @param client the game client
         * @param localLocation the center location of the AoE
         * @param size the size of the area (ie. 3x3 AoE evaluates to size 3)
         * @return a polygon representing the tiles in the area
         */
        fun getCanvasTileAreaPoly(localX: Int, localY: Int, size: Int = 1): Polygon? {
            val plane = clientData.getPlane()

            val swX = localX - size * LOCAL_TILE_SIZE / 2
            val swY = localY - size * LOCAL_TILE_SIZE / 2

            val neX = localX + size * LOCAL_TILE_SIZE / 2
            val neY = localY + size * LOCAL_TILE_SIZE / 2

            val tileSettings = clientData.getTileSettings()

            val sceneX = localX.ushr(LOCAL_COORD_BITS)
            val sceneY = localY.ushr(LOCAL_COORD_BITS)

            if (sceneX < 0 || sceneY < 0 || sceneX >= SCENE_SIZE || sceneY >= SCENE_SIZE) {
                return null
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

            if (p1 == null || p2 == null || p3 == null || p4 == null) {
                return null
            }

            val poly = Polygon()
            poly.addPoint(p1.getX().toInt(), p1.getY().toInt())
            poly.addPoint(p2.getX().toInt(), p2.getY().toInt())
            poly.addPoint(p3.getX().toInt(), p3.getY().toInt())
            poly.addPoint(p4.getX().toInt(), p4.getY().toInt())

            return poly
        }
    }
}