package com.p3achb0t.api.wrappers.utils

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Tile
import kotlin.math.abs
import kotlin.math.sign

class LineOfSight(val ctx: Context) {
    fun canReach(source: Tile, destination: Tile): Boolean {
        println("source: $source dest: $destination")
        if (source == destination) return false
        require(source.plane == destination.plane)
        val plane = source.plane
        val dx = destination.x - source.x
        val dy = destination.y - source.y
        val dxAbs = abs(dx)
        val dyAbs = abs(dy)

        //Figure out which direction this tile is located
        val xFlags = CollisionFlag.PROJECTILE_OBJECT or if (dx < 0) {
            CollisionFlag.PROJECTILE_EAST
        } else {
            CollisionFlag.PROJECTILE_WEST
        }
        val yFlags = CollisionFlag.PROJECTILE_OBJECT or if (dy < 0) {
            CollisionFlag.PROJECTILE_NORTH
        } else {
            CollisionFlag.PROJECTILE_SOUTH
        }

        var x = source.x
        var y = source.y

        val collisionMap = ctx.client.getCollisionMaps()

//        println("Found direciton. now computing")
        if (dxAbs > dyAbs) {
            var yExact = (y shl 16) + (1 shl 15)
            if (dy < 0) {
                yExact--
            }
            val yStep = (dy shl 16) / dxAbs
            val xStep = dx.sign
            while (x != destination.x) {
                x += xStep

                if ((collisionMap[plane].getFlags()[x][y] and xFlags) != 0) return false
                yExact += yStep
                val nextY = yExact ushr 16

                if (nextY != y && (collisionMap[plane].getFlags()[x][nextY] and yFlags) != 0) return false
                y = nextY
            }
        } else {
            var xExact = (x shl 16) + (1 shl 15)
            if (dx < 0) {
                xExact--
            }
            val xStep = (dx shl 16) / dyAbs
            val yStep = dy.sign
            while (y != destination.y) {
                y += yStep
                if ((collisionMap[plane].getFlags()[x][y] and yFlags) != 0) return false
                xExact += xStep
                val nextX = xExact ushr 16
                if (nextX != x && (collisionMap[plane].getFlags()[nextX][y] and xFlags) != 0) return false
                x = nextX
            }
        }
        return true
    }
}