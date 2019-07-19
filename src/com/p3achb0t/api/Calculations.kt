package com.p3achb0t.api

import com.p3achb0t.CustomCanvas
import com.p3achb0t.Main.Data.clientData
import com.p3achb0t.api.Constants.TILE_FLAG_BRIDGE
import com.p3achb0t.api.wrappers.ClientMode
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.interfaces.Locatable
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import java.awt.Point
import java.awt.Polygon
import java.awt.Rectangle
import java.awt.geom.Area
import kotlin.experimental.and
import kotlin.math.max
import kotlin.math.min


class Calculations {

    companion object {
        val LOCAL_COORD_BITS = 7
        val LOCAL_TILE_SIZE = 1 shl LOCAL_COORD_BITS // 128 - size of a tile in local coordinates
        val LOCAL_HALF_TILE_SIZE = LOCAL_TILE_SIZE / 2

        val SCENE_SIZE = Constants.SCENE_SIZE // in tiles
        var SINE = IntArray(2048)
        var COSINE = IntArray(2048)
        val GAMESCREEN = Rectangle(4, 4, 512, 334)
        var chatBoxDimensions = Rectangle()
        var miniMapDimensions = Rectangle()
        var inventoryDimensions = Rectangle()
        var inventoryBarTopDimensions = Rectangle()
        var inventoryBarBottomDimensions = Rectangle()
        val resizeableOffScreenAreas = ArrayList<Rectangle>()
        var screenInit = false

        init {
            for (i in 0 until SINE.size) {
                SINE[i] = (65536.0 * Math.sin(i.toDouble() * 0.0030679615)).toInt()
                COSINE[i] = (65536.0 * Math.cos(i.toDouble() * 0.0030679615)).toInt()
            }
        }

        fun getTileHeight(plane: Int, x: Int, y: Int): Int {
            val x1 = x shr 7
            val y1 = y shr 7
            val x2 = x and 127
            val y2 = y and 127
            if (x1 < 0 || y1 < 0 || x1 > 103 || y1 > 103) {
                return 0
            }

            var zidx = plane
            if (zidx < 3 && (2 and clientData.getTileSettings()[1][x1][y1].toInt()) == 2) {
                zidx++
            }

            val ground = clientData.getTileHeights()
            val i = ground[zidx][x1 + 1][y1] * x2 + ground[zidx][x1][y1] * (128 - x2) shr 7
            val j = ground[zidx][x1 + 1][y1 + 1] * x2 + ground[zidx][x1][y1 + 1] * (128 - x2) shr 7

            return j * y2 + (128 - y2) * i shr 7
        }

        /**
         * @param regionX
         * @param regionY
         * @param modelHeight
         * @return Point : Convert from tile to point on screen
         */
        fun worldToScreen(regionX: Int, regionY: Int, modelHeight: Int): Point {
            var x = regionX
            var y = regionY
            if (x < 128 || y < 128 || x > 13056 || y > 13056) {
                return Point(-1, -1)
            }
            var z = getTileHeight(clientData.getPlane(), x, y) - modelHeight
            x -= clientData.getCameraX()
            z -= clientData.getCameraZ()
            y -= clientData.getCameraY()

            val yaw = clientData.getCameraYaw()
            val pitch = clientData.getCameraPitch()

            val sinCurveY = SINE[pitch]
            val cosCurveY = COSINE[pitch]
            val sinCurveX = SINE[yaw]
            val cosCurveX = COSINE[yaw]

            var _angle = (y * sinCurveX + x * cosCurveX) shr 16

            y = y * cosCurveX - x * sinCurveX shr 16
            x = _angle

            _angle = z * cosCurveY - y * sinCurveY shr 16
            y = z * sinCurveY + y * cosCurveY shr 16
            z = _angle


            return if (y >= 50) {
                val screenX = x * clientData.getZoomExact() / y + CustomCanvas.dimension.width / 2
                val screenY = z * clientData.getZoomExact() / y + CustomCanvas.dimension.height / 2
                Point(screenX, screenY)
            } else Point(-1, -1)
        }

        fun initScreenWidgetDimentions() {
            // main screen 122,0
            //Mini map 164, 17
            val miniMapWidget = WidgetItem(
                Widgets.find(
                    WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID,
                    WidgetID.Viewport.MINIMAP_RESIZABLE_WIDGET
                )
            )
            miniMapDimensions = miniMapWidget.area


            //inventory bar 164,47(topbar), bottom 164,33
            val inventoryTop = WidgetItem(Widgets.find(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID, 47))
            inventoryBarTopDimensions = inventoryTop.area
            val inventoryBottom = WidgetItem(Widgets.find(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID, 33))
            inventoryBarBottomDimensions = inventoryBottom.area
            //chatbox 162,0
            val chatbox = WidgetItem(Widgets.find(WidgetID.CHATBOX_GROUP_ID, 0))
            chatBoxDimensions = chatbox.area
            val tabWidget = WidgetItem(Widgets.find(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID, 65))
            inventoryDimensions = tabWidget.area
            // Only set to true if login screen is not visible
            val login = Widgets.find(WidgetID.LOGIN_CLICK_TO_PLAY_GROUP_ID, 85)
            if (login == null) {
                screenInit = true
                resizeableOffScreenAreas.add(chatBoxDimensions)
                resizeableOffScreenAreas.add(miniMapDimensions)
                resizeableOffScreenAreas.add(inventoryBarBottomDimensions)
                resizeableOffScreenAreas.add(inventoryBarTopDimensions)
            }
        }

        fun isOnscreen(point: Point): Boolean {
            return if (ClientMode.getMode() == ClientMode.Companion.ModeType.FixedMode) {
                GAMESCREEN.contains(point)
            } else {
                if (!screenInit) initScreenWidgetDimentions()

                var isBehindInventory = false
                // Inventory if visible area:164,65
                if (Tabs.getOpenTab() != Tabs.Tab_Types.None) {
                    isBehindInventory = inventoryDimensions.contains(point)
                }

                !miniMapDimensions.contains(point)
                        && !inventoryBarTopDimensions.contains(point)
                        && !inventoryBarBottomDimensions.contains(point)
                        && !chatBoxDimensions.contains(point)
                        && !isBehindInventory
            }
        }

        fun isOnscreen(x: Int, y: Int): Boolean {
            return isOnscreen(Point(x, y))
        }

        fun isOnscreen(rectangle: Rectangle): Boolean {
            return if (ClientMode.getMode() == ClientMode.Companion.ModeType.FixedMode) {
                GAMESCREEN.intersects(rectangle)
            } else {
                if (!screenInit) initScreenWidgetDimentions()


                var isBehindInventory = false
                // Inventory if visible area:164,65
                if (Tabs.getOpenTab() != Tabs.Tab_Types.None) {
                    isBehindInventory = inventoryDimensions.intersects(rectangle)
                }

                !miniMapDimensions.intersects(rectangle)
                        && !inventoryBarTopDimensions.intersects(rectangle)
                        && !inventoryBarBottomDimensions.intersects(rectangle)
                        && !chatBoxDimensions.intersects(rectangle)
                        && !isBehindInventory
            }
        }

//        fun worldToMap(regionX: Int, regionY: Int): Point {
////            clientData.getMap
//            var x = regionX
//            var y = regionY
//            val local = Main.clientData.getLocalPlayer()
//            x -= Main.clientData.getBaseX()
//            y -= Main.clientData.getBaseY()
//
//            if (x > 104 || x < 0 || y > 104 || y < 0) {
//                return Point(-1, -1)
//            }
//            val regionTileX = local.getLocalX() - Main.clientData.getBaseX()
//            val regionTileY = local.getLocalY() - Main.clientData.getBaseY()
//
//            val cX = (x * 4 + 2 - (regionTileX shl 9) / 128f) as Int
//            val cY = (y * 4 + 2 - (regionTileY shl 9) / 128f) as Int
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

        @JvmStatic
        private fun lineIntersectionWithRect(A: Point, B: Point, rect: Rectangle): Point {
            var validPoint = Point(-1, -1)
            val topLeft = Point(rect.x, rect.y)
            val topRight = Point(rect.width, rect.y)
            val bottomLeft = Point(rect.x, rect.height)
            val bottomRight = Point(rect.width, rect.height)
            val p1 = lineLineIntersection(A, B, topLeft, topRight)
            val p2 = lineLineIntersection(A, B, topRight, bottomRight)
            val p3 = lineLineIntersection(A, B, bottomRight, bottomLeft)
            val p4 = lineLineIntersection(A, B, bottomLeft, topLeft)

            if (p1.x != -1 && p1.y != -1) validPoint = p1
            if (p2.x != -1 && p2.y != -1) validPoint = p2
            if (p3.x != -1 && p3.y != -1) validPoint = p3
            if (p4.x != -1 && p4.y != -1) validPoint = p4
            return validPoint
        }

        @JvmStatic
        private fun lineLineIntersection(A: Point, B: Point, C: Point, D: Point): Point {
            // Line AB represented as a1x + b1y = c1
            val a1 = (B.y - A.y).toDouble()
            val b1 = (A.x - B.x).toDouble()
            val c1 = a1 * A.x + b1 * A.y

            // Line CD represented as a2x + b2y = c2
            val a2 = (D.y - C.y).toDouble()
            val b2 = (C.x - D.x).toDouble()
            val c2 = a2 * C.x + b2 * C.y

            val determinant = a1 * b2 - a2 * b1
            // The lines are parallel. This is simplified
            // by returning a pair of FLT_MAX
            if (determinant == 0.0) return Point(-1, -1) else {
                val x = ((b2 * c1 - b1 * c2) / determinant).toInt()
                val y = ((a1 * c2 - a2 * c1) / determinant).toInt()

                // Points are all on a line with a slope BUT they could be outside the line segment
                //Cases would be, x cant be greater than max x on both points or less than the min on both points. Same goes for y
                val l1MaxX = max(A.x, B.x)
                val l1MinX = min(A.x, B.x)
                val l1MaxY = max(A.y, B.y)
                val l1MinY = min(A.y, B.y)
                val l2MaxX = max(A.x, B.x)
                val l2MinX = min(A.x, B.x)
                val l2MaxY = max(A.y, B.y)
                val l2MinY = min(A.y, B.y)
                return if (x in (l1MinX..l1MaxX)
                    && x in (l2MinX..l2MaxX)
                    && y in (l1MinY..l1MaxY)
                    && y in (l2MinY..l2MaxY)
                ) {
                    Point(x, y)
                } else {
                    Point(-1, -1)
                }

            }
        }

        fun rectToPolygon(rect: Rectangle): Polygon{

            val xPoints = intArrayOf(rect.x, rect.x + rect.width, rect.x + rect.width, rect.x)
            val yPoints = intArrayOf(rect.y, rect.y, rect.y + rect.height, rect.y + rect.height)
            return Polygon(xPoints, yPoints, 4)
        }

//        static Polygon RectangleToPolygon(Rectangle rect) {
//            int[] xpoints = {rect.x, rect.x + rect.width, rect.x + rect.width, rect.x}:
//            int[] ypoints = {rect.y, rect.y, rect.y + rect.height, rect.y + rect.height};
//            return new Polygon(xpoints, ypoints, 4);
//        }

        /**
         * Returns a polygon representing an area.
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

            //Return empty poloy if 1 is -1,-1
            if (p1 == Point(-1, -1) || p2 == Point(-1, -1) || p3 == Point(-1, -1) || p4 == Point(-1, -1)) {
                return Polygon()
            }

            val poly = Polygon()
            poly.addPoint(p1.x,p1.y)
            poly.addPoint(p2.x,p2.y)
            poly.addPoint(p3.x,p3.y)
            poly.addPoint(p4.x,p4.y)
            resizeableOffScreenAreas.forEach {
                val rectPoly = rectToPolygon(it)
                poly.contains(it)
                val area = Area(poly)
                area.subtract(Area(rectPoly))


            }




            // Segments, p1 -> p2, p2 -> p3, p3 -> p4, p4 -> p1
            val line1 = arrayListOf(p1, p2)
            val line2 = arrayListOf(p2, p3)
            val line3 = arrayListOf(p3, p4)
            val line4 = arrayListOf(p4, p1)
            val lines = arrayListOf(line1, line2, line3, line4)

            lines.forEach { line ->
                val mainPoint = line[0]
                val nextPoint = line[1]
                if (isOnscreen(mainPoint)) {
                    poly.addPoint(mainPoint.getX().toInt(), mainPoint.getY().toInt())
                } else {
                    addIntersectionWithOffscreen(mainPoint, nextPoint, poly)
                }

                // Also need to check if next point is off screen then we need to add that intersection
                if (!isOnscreen(nextPoint)) {
                    addIntersectionWithOffscreen(nextPoint, mainPoint, poly)
                }
            }

            return poly
        }

        // Finding the correct intersection between the 2 main points and the 4 lines of an off screen rectangle
        private fun addIntersectionWithOffscreen(mainPoint: Point, nextPoint: Point, poly: Polygon) {
            val validPoints = ArrayList<Point>()
            if (ClientMode.getMode() == ClientMode.Companion.ModeType.FixedMode) {
                val candidatePoint = lineIntersectionWithRect(mainPoint, nextPoint, GAMESCREEN)
                if (candidatePoint.x != -1 && candidatePoint.y != -1)
                    validPoints.add(candidatePoint)
            } else {
                //Collected rectangles of components/widgets that are deemed offscreen
                resizeableOffScreenAreas.forEach {
                    if (it.contains(mainPoint)) {
                        val candidatePoint = lineIntersectionWithRect(mainPoint, nextPoint, it)
                        if (candidatePoint.x != -1 && candidatePoint.y != -1)
                            validPoints.add(candidatePoint)
                    }
                }
                if (Tabs.getOpenTab() != Tabs.Tab_Types.None && inventoryDimensions.contains(mainPoint)) {
                    val candidatePoint = lineIntersectionWithRect(mainPoint, nextPoint, inventoryDimensions)
                    if (candidatePoint.x != -1 && candidatePoint.y != -1)
                        validPoints.add(candidatePoint)
                }
            }
            validPoints.forEach {
                poly.addPoint(it.getX().toInt(), it.getY().toInt())
            }
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