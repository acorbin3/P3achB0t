package com.p3achb0t.scripts.paint.debug

import com.p3achb0t.api.script.PaintScript
import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.wrappers.utils.VisibilityMap
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Debug","Walk Helper","Bot Team", "0.1")
class PaintWalkHelper : PaintScript() {
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

}

