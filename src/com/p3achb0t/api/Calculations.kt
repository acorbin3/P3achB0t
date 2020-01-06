package com.p3achb0t.api

import com.p3achb0t.api.Constants.TILE_FLAG_BRIDGE
import com.p3achb0t.api.wrappers.ClientMode
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import java.awt.Point
import java.awt.Polygon
import java.awt.Rectangle
import java.awt.geom.Area
import java.awt.geom.PathIterator
import kotlin.experimental.and
import kotlin.math.pow
import kotlin.math.sqrt


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
        var mainScreen = Rectangle()
        val resizeableOffScreenAreas = ArrayList<Rectangle>()
        var screenInit = false

        init {
            for (i in 0 until SINE.size) {
                SINE[i] = (65536.0 * Math.sin(i.toDouble() * 0.0030679615)).toInt()
                COSINE[i] = (65536.0 * Math.cos(i.toDouble() * 0.0030679615)).toInt()
            }
        }

        fun getTileHeight(ctx: Context, plane: Int, x: Int, y: Int): Int {
            val x1 = x shr 7
            val y1 = y shr 7
            val x2 = x and 127
            val y2 = y and 127
            if (x1 < 0 || y1 < 0 || x1 > 103 || y1 > 103) {
                return 0
            }

            var zidx = plane
            if (zidx < 3 && (2 and ctx.client.getTiles_renderFlags()[1][x1][y1].toInt()) == 2) {
                zidx++
            }

            val ground = ctx.client.getTiles_heights()
            val i = (ground[zidx][x1 + 1][y1] * x2 + ground[zidx][x1][y1] * (128 - x2)) shr 7
            val j = (ground[zidx][x1 + 1][y1 + 1] * x2 + ground[zidx][x1][y1 + 1] * (128 - x2)) shr 7

            return (j * y2 + (128 - y2) * i) shr 7
        }

        /**
         * @param regionX
         * @param regionY
         * @param modelHeight
         * @return Point : Convert from tile to point on screen
         */
        fun worldToScreen(regionX: Int, regionY: Int, modelHeight: Int, ctx: Context): Point {
            var x = regionX
            var y = regionY
            if (x < 128 || y < 128 || x > 13056 || y > 13056) {
                return Point(-1, -1)
            }
            var z = getTileHeight(ctx, ctx.client.getPlane(), x, y) - modelHeight
            x -= ctx.client.getCameraX()
            z -= ctx.client.getCameraZ()
            y -= ctx.client.getCameraY()

            val yaw = ctx.client.getCameraYaw()
            val pitch = ctx.client.getCameraPitch()

            val sinCurveY = SINE[pitch]
            val cosCurveY = COSINE[pitch]
            val sinCurveX = SINE[yaw]
            val cosCurveX = COSINE[yaw]

            var _angle = (y * sinCurveX + x * cosCurveX) shr 16

            y = ((y * cosCurveX) - (x * sinCurveX)) shr 16
            x = _angle

            _angle = (z * cosCurveY - y * sinCurveY) shr 16
            y = (z * sinCurveY + y * cosCurveY) shr 16
            z = _angle


            return if (y >= 50) {

                val screenX = x * ctx.client.getViewportZoom() / y + ctx.applet.size.width / 2
                val screenY = z * ctx.client.getViewportZoom() / y + ctx.applet.size.height / 2
                Point(screenX, screenY)
            } else Point(-1, -1)
        }

        fun midPoint(p1: Point, p2: Point): Point {
            return Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2)
        }

        fun initScreenWidgetDimentions(ctx: Context) {
            println("Init screenDimentions")
            // main screen 122,0
            //Mini map 164, 17
            val miniMapWidget = WidgetItem(
                    ctx.widgets.find(
                            WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID,
                            WidgetID.Viewport.MINIMAP_RESIZABLE_WIDGET
                    ),
                    ctx = ctx
            )
            miniMapDimensions = miniMapWidget.area


            //inventory bar 164,47(topbar), bottom 164,33
            val inventoryTop = WidgetItem(ctx.widgets.find(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID, 47), ctx = ctx)
            inventoryBarTopDimensions = inventoryTop.area
            val inventoryBottom = WidgetItem(ctx.widgets.find(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID, 33), ctx = ctx)
            inventoryBarBottomDimensions = inventoryBottom.area
            //chatbox 162,0
            val chatbox = WidgetItem(ctx.widgets.find(WidgetID.CHATBOX_GROUP_ID, 0), ctx = ctx)
            chatBoxDimensions = chatbox.area
            val tabWidget = WidgetItem(ctx.widgets.find(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID, 65), ctx = ctx)
            inventoryDimensions = tabWidget.area

            mainScreen = WidgetItem(ctx.widgets.find(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID, 0), ctx = ctx).area
            // Only set to true if login screen is not visible
            val login = WidgetItem(ctx.widgets.find(WidgetID.LOGIN_CLICK_TO_PLAY_GROUP_ID, 85), ctx = ctx)
            println("login x,y: ${login.area.x}, ${login.area.y}  inventoryDimensions: ${chatBoxDimensions.x},${chatBoxDimensions.y}")
            if (login.area.x == 0
                && login.area.y == 0
                && login.area.height == 0
                && login.area.width == 0
                && miniMapWidget.area.x > 500
                && chatBoxDimensions.y > 50
                && inventoryBarBottomDimensions.y > 10
                && inventoryBarTopDimensions.y > 50
            ) {
                screenInit = true
                resizeableOffScreenAreas.add(chatBoxDimensions)
                resizeableOffScreenAreas.add(miniMapDimensions)
                resizeableOffScreenAreas.add(inventoryBarBottomDimensions)
                resizeableOffScreenAreas.add(inventoryBarTopDimensions)
            }
        }

        fun isOnscreen(ctx: Context, point: Point): Boolean {
            return if (ctx.clientMode.getMode() == ClientMode.Companion.ModeType.FixedMode) {
                GAMESCREEN.contains(point)
            } else {
                if (!screenInit) initScreenWidgetDimentions(ctx)

                var isBehindInventory = false
                // Inventory if visible area:164,65
                if (ctx.tabs.getOpenTab() != Tabs.Tab_Types.None) {
                    isBehindInventory = inventoryDimensions.contains(point)
                }

                !miniMapDimensions.contains(point)
                        && !inventoryBarTopDimensions.contains(point)
                        && !inventoryBarBottomDimensions.contains(point)
                        && !chatBoxDimensions.contains(point)
                        && !isBehindInventory
            }
        }

        fun isOnscreen(ctx: Context, rectangle: Rectangle): Boolean {
            return if (ctx.clientMode.getMode() == ClientMode.Companion.ModeType.FixedMode) {
                GAMESCREEN.intersects(rectangle)
            } else {
                if (!screenInit) initScreenWidgetDimentions(ctx)


                var isBehindInventory = false
                // Inventory if visible area:164,65
                if (ctx.tabs.getOpenTab() != Tabs.Tab_Types.None) {
                    isBehindInventory = inventoryDimensions.intersects(rectangle)
                }

                !miniMapDimensions.intersects(rectangle)
                        && !inventoryBarTopDimensions.intersects(rectangle)
                        && !inventoryBarBottomDimensions.intersects(rectangle)
                        && !chatBoxDimensions.intersects(rectangle)
                        && !isBehindInventory
                        && mainScreen.intersects(rectangle)
            }
        }


        // This will convert the regional coordinates to the miniMap
        fun worldToMiniMap(x: Int, y: Int, ctx: Context): Point {

            // Note: Multiply by tile size before converting to local coordinates to preserve precision
            val tilePX = ((x - ctx.client.getBaseX()) * Constants.MAP_TILE_SIZE) shr Constants.REGION_SHIFT
            val tilePY = ((y - ctx.client.getBaseY()) * Constants.MAP_TILE_SIZE) shr Constants.REGION_SHIFT
            val local = ctx.client.getLocalPlayer()

            val playerPX =
                ((local.getX() - ctx.client.getBaseX()) * Constants.MAP_TILE_SIZE) shr Constants.REGION_SHIFT
            val playerPY =
                ((local.getY() - ctx.client.getBaseY()) * Constants.MAP_TILE_SIZE) shr Constants.REGION_SHIFT


            val diffX = tilePX - playerPX
            val diffY = tilePY - playerPY

            val miniMapWidget = ctx.miniMap.getWidget() ?: return Point(-1, -1)

            val angle = ctx.client.getCamAngleY() and 0x7ff

            val sineCalc = SINE[angle]
            val cosCalc = COSINE[angle]

            val calcCenterX = (sineCalc * diffY + cosCalc * diffX) shr Constants.TRIG_SHIFT
            val calcCenterY = (sineCalc * diffX - cosCalc * diffY) shr Constants.TRIG_SHIFT

            val screenX = calcCenterX + Widget.getWidgetX(miniMapWidget, ctx) + miniMapWidget.getWidth() / 2
            val screenY = calcCenterY + Widget.getWidgetY(miniMapWidget, ctx) + miniMapWidget.getHeight() / 2
            return if (ctx.miniMap.getMapArea().contains(Point(screenX, screenY))) {
                Point(screenX - 2, screenY - 1)
            } else Point(-1, -1)
        }

        private fun getHeight(localX: Int, localY: Int, plane: Int, ctx: Context): Int {
            val sceneX = localX shr LOCAL_COORD_BITS
            val sceneY = localY shr LOCAL_COORD_BITS
            if (sceneX >= 0 && sceneY >= 0 && sceneX < SCENE_SIZE && sceneY < SCENE_SIZE) {
                val tileHeights = ctx.client.getTiles_heights()

                val x = localX.and(LOCAL_TILE_SIZE)
                val y = localY.and(LOCAL_TILE_SIZE)
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
         * @param localLocation the center location of the AoE in regional coordinates
         * @param size the size of the area (ie. 3x3 AoE evaluates to size 3)
         * @return a polygon representing the tiles in the area
         */
        fun getCanvasTileAreaPoly(ctx: Context, localX: Int, localY: Int, size: Int = 1): Polygon {
            val plane = ctx.client.getPlane()

            val swX = localX - size * LOCAL_TILE_SIZE / 2
            val swY = localY - size * LOCAL_TILE_SIZE / 2

            val neX = localX + size * LOCAL_TILE_SIZE / 2
            val neY = localY + size * LOCAL_TILE_SIZE / 2

            val tileSettings = ctx.client.getTiles_renderFlags()

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

            val swHeight = getHeight(swX, swY, tilePlane, ctx)
            val nwHeight = getHeight(neX, swY, tilePlane, ctx)
            val neHeight = getHeight(neX, neY, tilePlane, ctx)
            val seHeight = getHeight(swX, neY, tilePlane, ctx)

            val p1 = worldToScreen(swX, swY, swHeight, ctx)
            val p2 = worldToScreen(neX, swY, nwHeight, ctx)
            val p3 = worldToScreen(neX, neY, neHeight, ctx)
            val p4 = worldToScreen(swX, neY, seHeight, ctx)

            //Return empty poloy if 1 is -1,-1
            if (p1 == Point(-1, -1) || p2 == Point(-1, -1) || p3 == Point(-1, -1) || p4 == Point(-1, -1)) {
                return Polygon()
            }

            val poly = Polygon()
            poly.addPoint(p1.x,p1.y)
            poly.addPoint(p2.x,p2.y)
            poly.addPoint(p3.x,p3.y)
            poly.addPoint(p4.x,p4.y)

            //Covert polygon to an Area and each rectangle to an Area. With the area class you can use subtract
            // as an easy way to computer the masked section.
            val polyArea = Area(poly)
            resizeableOffScreenAreas.forEach {
                if (poly.intersects(it))
                    polyArea.subtract(Area(it))
            }
            if (ctx.tabs.getOpenTab() != Tabs.Tab_Types.None && poly.intersects(inventoryDimensions)) {
                polyArea.subtract(Area(inventoryDimensions))
            }
            // Convert back to polygon using the path iterator.
            val convertedPoly = Polygon()
            val floats = floatArrayOf(0F, 0F, 0F, 0F, 0F, 0F)
            val iterator = polyArea.getPathIterator(null)
            while (!iterator.isDone) {
                val type = iterator.currentSegment(floats)
                val x = floats[0].toInt()
                val y = floats[1].toInt()
                if (type != PathIterator.SEG_CLOSE) {
                    convertedPoly.addPoint(x, y)
                }
                iterator.next()
            }

            return convertedPoly
        }

        fun distanceBetween(a: Tile, b: Tile): Int {
            return distanceBetween(a.x, a.y, b.x, b.y)
        }

        fun distanceBetween(a: Point, b: Point): Int {
            return distanceBetween(a.x, a.y, b.x, b.y)
        }

        fun distanceBetween(x: Int, y: Int, x1: Int, y1: Int): Int {
            return sqrt((x1 - x).toDouble().pow(2.0) + (y1 - y).toDouble().pow(2.0)).toInt()
        }

        /**
         * @param a tile
         * @return current distance between player and specific tile
         */
        fun distanceTo(a: Tile, ctx: Context): Int {
            val loc = ctx.players.getLocal().getGlobalLocation()
            return distanceBetween(a.x, a.y, loc.x, loc.y)
        }


        fun convertAreaToPolygon(area: Area, poly: Polygon) {
            val floatsFinal = floatArrayOf(0F, 0F, 0F, 0F, 0F, 0F)
            val iteratorFinal = area.getPathIterator(null)
            while (!iteratorFinal.isDone) {
                val type = iteratorFinal.currentSegment(floatsFinal)
                val x = floatsFinal[0].toInt()
                val y = floatsFinal[1].toInt()
                if (type != PathIterator.SEG_CLOSE) {
                    poly.addPoint(x, y)
                }
                iteratorFinal.next()
            }
        }
    }
}