package com.p3achb0t.scripts.debug.paint

import com.p3achb0t.api.DebugScript
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.wrappers.utils.VisibilityMap
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Debug","Walk Helper","Bot Team", "0.1")
class DebugWalkHelper : DebugScript() {
    override fun draw(g: Graphics) {
        if (ctx.client.getGameState() == 30) {
            for(tile in VisibilityMap(ctx).visibleTiles()){
                if(tile.canReach()) {
                    g.color = Color.GREEN
                    val mapPoint = tile.getGlobalLocation().getMiniMapPoint()
                    g.fillRect(mapPoint.x, mapPoint.y, 4, 4)
                    g.color = Color.BLACK
                    g.drawRect(mapPoint.x, mapPoint.y, 4, 4)
                }
            }
        }
    }

    override fun start() {

    }

    override fun stop() {

    }
}

