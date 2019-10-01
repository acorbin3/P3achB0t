package com.p3achb0t.api.painting

import com.p3achb0t._runestar_interfaces.Npc
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getActorTriangles
import com.p3achb0t.api.getConvexHull
import com.p3achb0t.api.Context
import java.awt.Color
import java.awt.Graphics

fun paintNPCs(g: Graphics, ctx: Context) {
    try {
        ///////NPC paint//////////
        var count = 0
        count = 0
        val localNpcs = ctx.client.getNpcs()
        var npc: Npc? = null
        localNpcs.iterator().forEach {
            if (it != null) {
                npc = it

                //                                print("Name: ${it.getComposite().getName()}, ID:${it.getType()?.getId()} x:${it.getX()} y:${it.getY()},")
                count += 1

                val tile =
                    Calculations.getCanvasTileAreaPoly(ctx, it.getX(), it.getY())
                g.color = Color.CYAN
                g.drawPolygon(tile)
                g.color = Color(0, 0, 0, 50)
                g.fillPolygon(tile)

                val polygon = npc?.getType()?.getId()?.toLong()?.let { it1 ->
                    getActorTriangles(
                            npc, ctx.client.getNPCType_cachedModels(),
                            it1, ctx

                    )
                }
                g.color = Color.BLUE
                polygon?.forEach {
                    g.drawPolygon(it)
                }
                g.color = Color.YELLOW
                val mapPoint = Calculations.worldToMiniMap(it.getX(), it.getY(), ctx)
                g.fillRect(mapPoint.x, mapPoint.y, 4, 4)

                val ch = getConvexHull(
                        npc,
                        ctx.client.getNPCType_cachedModels(),
                        npc!!.getType().getId().toLong(), ctx

                )
                g.color = Color.PINK
                g.drawPolygon(ch)

                val namePoint =
                    Calculations.worldToScreen(
                            it.getX(),
                            it.getY(),
                            it.getHeight(), ctx

                    )
                if (namePoint.x != -1 && namePoint.y != -1 && Calculations.isOnscreen(ctx,namePoint )) {
                    g.color = Color.GREEN
                    g.drawString(
                        "${it.getType().getName()} ${it.getType().getId()} ${it.getSequence()}",
                        namePoint.x,
                        namePoint.y
                    )
                }
            }
        }
    } catch (e: Exception) {
        println("Error: NPC Paint " + e.message)
        e.stackTrace.iterator().forEach {
            println(it)
        }
    }
}