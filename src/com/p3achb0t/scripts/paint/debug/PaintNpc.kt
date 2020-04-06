package com.p3achb0t.scripts.paint.debug

import com.p3achb0t.api.PaintScript
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.interfaces.Npc
import com.p3achb0t.api.wrappers.NPC
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.getActorTriangles
import com.p3achb0t.api.wrappers.utils.getConvexHull
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Debug", "NPC Helper", "Bot Team", "0.1")
class PaintNpc : PaintScript() {

    override fun draw(g: Graphics) {
        if (ctx.client.getGameState() == 30) {

            try {

                var count = 0
                val localNpcs = ctx.client.getNpcs()

                var npc: Npc? = null
                localNpcs.forEachIndexed { index, npci ->
                    npc = npci
                    if (npc != null) {
                        val newNPC = NPC(npci, ctx, index)

                        //                                print("Name: ${it.getComposite().getName()}, ID:${it.getType()?.getId()} x:${it.getX()} y:${it.getY()},")
                        if (newNPC.isOnScreen() && newNPC.isMouseOverObj()) {
                            count += 1

                            val tile =
                                    Calculations.getCanvasTileAreaPoly(ctx, npci.getX(), npci.getY())
                            g.color = Color.CYAN
                            g.drawPolygon(tile)
                            g.color = Color(0, 0, 0, 50)
                            g.fillPolygon(tile)

                            val polygon = npc?.getType()?.getId()?.toLong()?.let { it1 ->
                                getActorTriangles(
                                        npc, ctx

                                )
                            }
                            g.color = Color.BLUE
                            polygon?.forEach {
                                g.drawPolygon(it)
                            }
                            g.color = Color.YELLOW
                            val mapPoint = Calculations.worldToMiniMap(npci.getX(), npci.getY(), ctx)
                            g.fillRect(mapPoint.x, mapPoint.y, 4, 4)

                            val ch = getConvexHull(
                                    npc,
                                    ctx

                            )
                            g.color = Color.PINK
                            g.drawPolygon(ch)

                            val namePoint = newNPC.getNamePoint()

                            if (namePoint.x != -1 && namePoint.y != -1 && Calculations.isOnscreen(ctx, namePoint)) {
                                g.color = Color.GREEN
                                g.drawString(
                                        "${npci.getType().getName()} ${npci.getType().getId()} ${npci.getSequence()} targIndex: ${newNPC.npc.getTargetIndex()} orientation: ${newNPC.npc.getOrientation()}",
                                        namePoint.x,
                                        namePoint.y
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            g.drawString("Please Login to use ${ctx.client.getGameState()}", 50, 50)
        }
    }
}