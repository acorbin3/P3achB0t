package com.p3achb0t.api.painting

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getActorTriangles
import com.p3achb0t.api.getConvexHull
import java.awt.Color
import java.awt.Graphics

fun playerPaint(g: Graphics) {
    try {
        ///////Player paint//////////
        g.color = Color.GREEN
        val players = Main.clientData.getPlayers()
        var count = 0
        players.iterator().forEach { _player ->
            if (_player != null && _player.getLevel() > 0) {
                //                                println("${_player.getName()} ${_player.getLevel()} x:${_player.getLocalX()} y: ${_player.getLocalY()}, ")
                //                                print("Queue size: ${_player.getQueueSize()}")

                count += 1


                val tile = Calculations.getCanvasTileAreaPoly(
                    _player.getLocalX(),
                    _player.getLocalY()
                )
                g.color = Color.CYAN
                g.drawPolygon(tile)
                g.color = Color(0, 0, 0, 50)
                g.fillPolygon(tile)

                val polygon = getActorTriangles(
                    _player,
                    Main.clientData.getPlayerModelCache(),
                    _player.getComposite().getStaticModelID()
                )
                g.color = Color.YELLOW
                polygon.forEach {
                    g.drawPolygon(it)
                }
                val ch = getConvexHull(
                    _player,
                    Main.clientData.getPlayerModelCache(),
                    _player.getComposite().getStaticModelID()
                )
                g.color = Color.RED
                g.drawPolygon(ch)

                val namePoint = Calculations.worldToScreen(
                    _player.getLocalX(),
                    _player.getLocalY(),
                    _player.getModelHeight()
                )
                if (namePoint.x != -1 && namePoint.y != -1 && Calculations.isOnscreen(
                        namePoint
                    )
                ) {
                    g.color = Color.GREEN
                    g.drawString(
                        _player.getName().getName() + " ${_player.getAnimation()}  ${_player.getInteracting()}",
                        namePoint.x,
                        namePoint.y
                    )
                }

                g.color = Color.GREEN
                val mapPoint =
                    Calculations.worldToMiniMap(_player.getLocalX(), _player.getLocalY())
                g.fillRect(mapPoint.x, mapPoint.y, 4, 4)
            }
        }
    } catch (e: Exception) {
        println("Error: Player pain " + e.message)
    }
}