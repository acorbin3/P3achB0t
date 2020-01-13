package com.p3achb0t.scripts.paint_debug

import com.p3achb0t.api.Context
import java.awt.Color
import java.awt.Graphics

fun groundItemsPaint(g: Graphics, ctx: Context) {
    try {
        val loot: IntArray = intArrayOf(2363, 1127, 1079, 1303, 1347, 4087, 4180, 4585, 1149, 892, 21880, 562, 560, 212, 208, 3052, 220, 19580, 9381, 1616, 452, 19582,
                21930, 995, 21918, 22103, 11286, 1333, 536)
//        val groundItems = ctx.groundItems.getItempred(loot)
        val groundItems = ctx.groundItems.getAllItems()

        groundItems.forEach {

            if (it.isOnScreen() && it.stackSize > 0) {

                val namePoint = it.getNamePoint()
//                g.drawString(it.id.toString(), namePoint.x, namePoint.y)
                g.color = Color.GREEN
                val name = ctx.cache.getItemName(it.id)
                g.drawString(
                        "$name(${it.id}) ${it.stackSize}",
                        namePoint.x,
                        namePoint.y
                )
                println("$name(${it.id}) ${it.stackSize}")


                val triangles = it.getTriangles()
                g.color = Color.CYAN
                triangles.forEach { poly ->
                    g.drawPolygon(poly)
                }

                val ch1 = it.getConvexHull()
                g.color = Color.RED
                g.drawPolygon(ch1)
            }
        }
    } catch (e: Exception) {
        println("Error: GroundItems Paint " + e.message)
        e.stackTrace.iterator().forEach {
            println(it)
        }
    }
}