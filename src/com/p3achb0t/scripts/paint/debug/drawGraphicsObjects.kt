package com.p3achb0t.scripts.paint.debug

import com.p3achb0t.api.Context
import com.p3achb0t.api.interfaces.GraphicsObject
import com.p3achb0t.api.wrappers.Projectile
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo
import com.p3achb0t.api.wrappers.utils.getTrianglesFromModel
import java.awt.Color
import java.awt.Graphics
import java.awt.Point

fun drawGraphicsObjects(g: Graphics, ctx: Context) {
    if (ctx.client.getGameState() == 30) {
        val graphicsObjects = ctx.client.getGraphicsObjects()
        val sentinel = graphicsObjects.getSentinel()
        var cur = sentinel.getPrevious()
        val color = g.color
        g.color = Color.MAGENTA
        while(cur != null && cur != sentinel){
            if(cur is GraphicsObject){
                val x = cur.getX()
                val y = cur.getY()
                val heigth = cur.getHeight()
                val point =  ctx?.client.let { it?.let { it1 -> Calculations.worldToScreen(x, y, heigth, ctx) } }
                        ?: Point(-1, -1)


                val tileHeight = Calculations.getTileHeight(ctx, cur.getPlane(), cur.getX().toInt(), cur.getY().toInt())

                val height =  tileHeight - cur.getPlane().toInt()
                val position: ObjectPositionInfo = ObjectPositionInfo(cur.getX().toInt(),
                            cur.getY().toInt(),
                            orientation = 0,
                            plane = cur.getPlane(),
                            z = cur.getPlane(),
                            tileHeight = tileHeight)
                val modelTriangles =
                        getTrianglesFromModel(
                                position,
                                cur.getModel(),
                                ctx

                        )
                g.color = Color.RED
                modelTriangles.forEach {
                    g.drawPolygon(it)
                }

                g.color = Color.GREEN
                g.drawString("${cur.getId()}",point.x, point.y)
                
            }
            cur = cur.getPrevious()
        }
        
        g.color = color
        
    }
}