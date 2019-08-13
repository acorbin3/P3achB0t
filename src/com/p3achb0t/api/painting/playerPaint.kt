package com.p3achb0t.api.painting

import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getActorTriangles
import com.p3achb0t.api.getConvexHull
import com.p3achb0t.api.wrappers.Client
import java.awt.Color
import java.awt.Graphics

fun playerPaint(g: Graphics) {
    try {
        ///////Player paint//////////
        g.color = Color.GREEN
        val players = Client.client.getPlayers()
        var count = 0
        players.iterator().forEach { _player ->
            if (_player != null && _player.getCombatLevel() > 0) {

                count += 1


                val tile = Calculations.getCanvasTileAreaPoly(
                    _player.getX(),
                    _player.getY()
                )
                g.color = Color.CYAN
                g.drawPolygon(tile)
                g.color = Color(0, 0, 0, 50)
                g.fillPolygon(tile)

                val polygon = getActorTriangles(
                    _player,
                    Client.client.getPlayerAppearance_cachedModels(),
                    _player.getAppearance().get__l()
                )
                g.color = Color.YELLOW
                polygon.forEach {
                    g.drawPolygon(it)
                }
                val ch = getConvexHull(
                    _player,
                    Client.client.getPlayerAppearance_cachedModels(),
                    _player.getAppearance().get__l()
                )
                g.color = Color.RED
                g.drawPolygon(ch)

                val namePoint = Calculations.worldToScreen(
                    _player.getX(),
                    _player.getY(),
                    _player.getHeight()
                )
                if (namePoint.x != -1 && namePoint.y != -1 && Calculations.isOnscreen(
                        namePoint
                    )
                ) {
                    g.color = Color.GREEN
                    g.drawString(
                        _player.getUsername().getCleanName() + " ${_player.getSequence()}  ${_player.getTargetIndex()} ${_player.getMovementSequence()} ${_player.getReadySequence()}",
                        namePoint.x,
                        namePoint.y
                    )
                }

                g.color = Color.GREEN
                val mapPoint =
                    Calculations.worldToMiniMap(_player.getX(), _player.getY())
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