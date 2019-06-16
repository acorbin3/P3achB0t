package com.p3achb0t.api

import com.p3achb0t.Main.Data.clientData
import javafx.scene.Camera
import java.awt.Point

class Calculations {

    companion object {
        var SINE = IntArray(2048)
        var COSINE = IntArray(2048)
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
            val itemSelected = clientData.getExperience()
            val settings = clientData.getSettings()
            val tileSettings = clientData.getTileSettings()
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
            clientData.getPlane()
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

//        fun worldToMap(regionX: Int, regionY: Int): Point {
//
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
    }
}