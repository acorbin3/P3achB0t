package com.p3achb0t.api.wrappers.utils

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Tile

class VisibilityMap(val ctx: Context) {
    fun isVisible(tile: Tile): Boolean{
        val vpitch = (ctx.client.getCameraPitch() - 128) / 32
        if (vpitch !in 0..7) return false
        val vx = tile.x - (ctx.camera.x ushr 7) + 25
        if (vx !in 0..50) return false
        val vy = tile.y - (ctx.camera.y ushr 7) + 25
        if (vy !in 0..50) return false
        val vyaw = ctx.camera.yaw / 64
        return ctx.client.getVisibilityMap()[vpitch][vyaw][vx][vy]
    }
    
    fun visibleTiles(): Sequence<Tile>{
        val vpitch = (ctx.client.getCameraPitch() - 128) / 32
        //println("vpitch: $vpitch")

        if (vpitch !in 0..7) return emptySequence()
        val vyaw = (ctx.camera.yaw % 2048)/ 64

        val map = ctx.client.getVisibilityMap()
//        println("vpitch:$vpitch, vyaw:$vyaw xlen: ${map.size}. ylen: ${map[0].size} basex:${ctx.client.getBaseX()} basey:${ctx.client.getBaseY()} ")
        val xymap = map[vpitch][vyaw]
//        val xymap = ctx.client.getVisibleTiles()
        val camX = ((ctx.camera.x - ctx.client.getBaseX()) shr 7 )  + ctx.client.getBaseX()
        val camY = ((ctx.camera.y - ctx.client.getBaseY()) shr 7 )  + ctx.client.getBaseY()
        println("camera.x,y,z: (${ctx.camera.x},${ctx.camera.y},${ctx.camera.z}) ($camX,$camY)")
        val camTile = Tile(camX,camY, ctx=ctx).getLocalLocation()
        //println(camTile)
        return Sequence {
            object : AbstractIterator<Tile>() {

                private var x = 0

                private var y = 0

                override fun computeNext() {
                    var found = false
                    while(!found) {
                        if (xymap[x][y]) {
                            found = true
                            val baseX = ctx.client.getBaseX()
                            val baseY = ctx.client.getBaseY()
                            val t = Tile(x + camTile.x  + baseX , y + camTile.y  + baseY, camTile.plane,ctx=ctx)
//                            print(t)
                            setNext(t)
                        }
                        if (y == 50) {
                            if (x == 50) {
                                return done()
                            } else {
                                x++
                                y = 0
                            }
                        } else {
                            y++
                        }
                    }
                }
            }
        }
    }
}