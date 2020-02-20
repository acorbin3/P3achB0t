package com.p3achb0t.scripts.paint_debug

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.getActorTriangles
import com.p3achb0t.api.wrappers.utils.getConvexHull
import java.awt.Color
import java.awt.Graphics

fun playerPaint(g: Graphics, ctx: Context) {
    try {
        ///////Player paint//////////
        g.color = Color.GREEN
        val players = ctx.client.getPlayers()
        var count = 0
        players.iterator().forEach { _player ->
            if (_player != null && _player.getCombatLevel() > 0) {

                count += 1


                val tile = Calculations.getCanvasTileAreaPoly(
                        ctx,
                        _player.getX(),
                        _player.getY()
                )
                g.color = Color.CYAN
                g.drawPolygon(tile)
                g.color = Color(0, 0, 0, 50)
                g.fillPolygon(tile)
                val polygon = getActorTriangles(
                        _player,
                        ctx

                )
                g.color = Color.YELLOW
                polygon.forEach {
                    g.drawPolygon(it)
                }
                val ch = getConvexHull(
                        _player,
                        ctx

                )
                g.color = Color.RED
                g.drawPolygon(ch)

                val namePoint = Calculations.worldToScreen(
                        _player.getX(),
                        _player.getY(),
                        _player.getHeight(), ctx

                )
                if (namePoint.x != -1 && namePoint.y != -1 && Calculations.isOnscreen(
                                ctx,namePoint
                        )
                ) {
                    g.color = Color.GREEN
                    g.drawString(
                        _player.getUsername().getCleanName() + " ${_player.getSequence()}  ${_player.getTargetIndex()} ${_player.getMovementSequence()} ${_player.getReadySequence()} ${_player.getOrientation()}",
                        namePoint.x,
                        namePoint.y
                    )
                }

                g.color = Color.GREEN
                val mapPoint =
                    Calculations.worldToMiniMap(_player.getX(), _player.getY(), ctx)
                g.fillRect(mapPoint.x, mapPoint.y, 4, 4)
            }
        }
    } catch (e: Exception) {
        println("Error: Player pain " + e.message)
        e.stackTrace.iterator().forEach {
            println(it)
        }
    }
}