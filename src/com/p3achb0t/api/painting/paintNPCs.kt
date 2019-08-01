package com.p3achb0t.api.painting

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getActorTriangles
import com.p3achb0t.api.getConvexHull
import com.p3achb0t.hook_interfaces.Npc
import java.awt.Color
import java.awt.Graphics

fun paintNPCs(g: Graphics) {
    try {
        ///////NPC paint//////////
        var count = 0
        count = 0
        val localNpcs = Main.clientData.getLocalNPCs()
        var npc: Npc? = null
        localNpcs.iterator().forEach {
            if (it != null) {
                npc = it

                //                                print("Name: ${it.getComposite().getName()}, ID:${it.getComposite().getNpcComposite_id()} x:${it.getLocalX()} y:${it.getLocalY()},")
                count += 1

                val tile =
                    Calculations.getCanvasTileAreaPoly(it.getLocalX(), it.getLocalY())
                g.color = Color.CYAN
                g.drawPolygon(tile)
                g.color = Color(0, 0, 0, 50)
                g.fillPolygon(tile)

                val polygon = npc?.getComposite()?.getNpcComposite_id()?.toLong()?.let { it1 ->
                    getActorTriangles(
                        npc, Main.clientData.getNpcModelCache(),
                        it1
                    )
                }
                g.color = Color.BLUE
                polygon?.forEach {
                    g.drawPolygon(it)
                }
                g.color = Color.YELLOW
                val mapPoint = Calculations.worldToMiniMap(it.getLocalX(), it.getLocalY())
                g.fillRect(mapPoint.x, mapPoint.y, 4, 4)

                val ch = getConvexHull(
                    npc,
                    Main.clientData.getNpcModelCache(),
                    npc!!.getComposite().getNpcComposite_id().toLong()
                )
                g.color = Color.PINK
                g.drawPolygon(ch)

                val namePoint =
                    Calculations.worldToScreen(
                        it.getLocalX(),
                        it.getLocalY(),
                        it.getModelHeight()
                    )
                if (namePoint.x != -1 && namePoint.y != -1 && Calculations.isOnscreen(namePoint)) {
                    g.color = Color.GREEN
                    g.drawString(
                        "${it.getComposite().getName()} ${it.getComposite().getNpcComposite_id()} ${it.getAnimation()}",
                        namePoint.x,
                        namePoint.y
                    )
                }
            }
        }
    } catch (e: Exception) {
        println("Error: NPC Paint " + e.message)
        e.stackTrace.iterator().forEach {
            print(it)
        }
    }
}