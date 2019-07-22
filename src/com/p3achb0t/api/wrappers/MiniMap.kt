package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Calculations.Companion.convertAreaToPolygon
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import com.p3achb0t.hook_interfaces.Widget
import java.awt.Polygon
import java.awt.geom.*

class MiniMap {

    companion object {
        private val PARENT = WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID
        private val CHILD = WidgetID.Viewport.MINIMAP_RESIZABLE_DRAW_AREA
        private var mapCircle = Polygon()
        var mapInit = false

        fun getWidget(): Widget? {
            return Widgets.find(PARENT, CHILD)
        }

        fun getMapArea(): Polygon {
            return if (mapInit) {
                mapCircle
            } else {

                val rect = WidgetItem(Widgets.find(PARENT, CHILD)).area
                // Before login the widget thinks its a position 0,0 which is incorrect.
                if (rect.centerX == 0.0) {
                    return Polygon()
                }
                val miniMapRadius = 76.0
                val ellipse2D = Ellipse2D.Double(
                    rect.centerX - miniMapRadius,
                    rect.centerY - miniMapRadius,
                    miniMapRadius * 2,
                    miniMapRadius * 2
                )
                val convertedPoly = Polygon()
                computeCirclePolygon(ellipse2D, convertedPoly)

                //Small globe that cuts int the mini map
                val globeParent = 160
                val globeChild = 42
                val rect2 = WidgetItem(Widgets.find(globeParent, globeChild)).area
                val globeRadius = 15.0
                val globeEllipse2D = Ellipse2D.Double(
                    rect2.centerX - globeRadius,
                    rect2.centerY - globeRadius,
                    globeRadius * 2,
                    globeRadius * 2
                )
                val convertedPolySmall = Polygon()
                computeCirclePolygon(globeEllipse2D, convertedPolySmall)

                //Convert polygons into Areas and do Area subtraction.
                val area = Area(convertedPoly)
                area.subtract(Area(convertedPolySmall))

                // Convert Area to a polygon
                val finalPoly = Polygon()
                convertAreaToPolygon(area, finalPoly)

                mapCircle = finalPoly
                mapInit = true
                mapCircle
            }
        }

        private fun computeCirclePolygon(ellipse2D: Ellipse2D.Double, convertedPoly: Polygon) {
            val floats = floatArrayOf(0F, 0F, 0F, 0F, 0F, 0F)
            val iterator = FlatteningPathIterator(ellipse2D.getPathIterator(AffineTransform()), 1.0)
            while (!iterator.isDone) {
                val type = iterator.currentSegment(floats)
                val x = floats[0].toInt()
                val y = floats[1].toInt()
                if (type != PathIterator.SEG_CLOSE) {
                    convertedPoly.addPoint(x, y)
                }
                iterator.next()
            }
        }

        suspend fun clickCoord(x: Int, y: Int) {

        }

    }
}