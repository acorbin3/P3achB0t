package com.p3achb0t.scripts.paint.debug

import com.p3achb0t.api.Context
import com.p3achb0t.api.PaintScript
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Debug","Ground Items Helper","Bot Team", "0.1")
class PaintGroundItems : PaintScript() {
    override fun draw(g: Graphics) {
        if (ctx.client.getGameState() == 30) {
            val loot: IntArray = intArrayOf(2363, 1127, 1079, 1303, 1347, 4087, 4180, 4585, 1149, 892, 21880, 562, 560, 212, 208, 3052, 220, 19580, 9381, 1616, 452, 19582,
                    21930, 995, 21918, 22103, 11286, 1333, 536)
//        val groundItems = ctx.groundItems.getItempred(loot)
            val groundItems = ctx.groundItems.getAllItems()
            val tileCount: HashMap<ObjectPositionInfo,Int> = HashMap()
            groundItems.forEach {
                if (it.isOnScreen() && it.stackSize > 0) {
                    if(it.position in tileCount){
                        tileCount[it.position]?.plus(1)?.let { it1 -> tileCount.put(it.position, it1) }
                    }else{
                        tileCount[it.position] = 1
                    }
                    val count = tileCount[it.position]
                    val namePoint = it.getNamePoint()
//                g.drawString(it.id.toString(), namePoint.x, namePoint.y)
                    g.color = Color.GREEN
                    val name = ctx.cache.getItemName(it.id)
                    g.drawString(
                            "$name(${it.id}) ${it.stackSize}",
                            namePoint.x,
                            namePoint.y - (count?.times(10)!!)
                    )

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
        }
    }

}

fun groundItemsPaint(g: Graphics, ctx: Context) {
    try {

    } catch (e: Exception) {
        println("Error: GroundItems Paint " + e.message)
        e.stackTrace.iterator().forEach {
            println(it)
        }
    }
}