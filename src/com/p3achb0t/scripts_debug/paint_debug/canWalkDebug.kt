package com.p3achb0t.scripts_debug.paint_debug

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.VisibilityMap
import java.awt.Color
import java.awt.Graphics

fun canWalkDebug(g: Graphics, ctx: Context) {

    for(tile in VisibilityMap(ctx).visibleTiles()){
//        println(tile)
        if(tile.canReach()) {

            g.color = Color.GREEN
            println("Tile walkable: $tile. Glob: ${tile.getGlobalLocation()}")
            val mapPoint = tile.getGlobalLocation().getMiniMapPoint()
            println("Mappoint:" + mapPoint)
            g.fillRect(mapPoint.x, mapPoint.y, 4, 4)
            g.color = Color.BLACK
            g.drawRect(mapPoint.x, mapPoint.y, 4, 4)
        }
    }
}