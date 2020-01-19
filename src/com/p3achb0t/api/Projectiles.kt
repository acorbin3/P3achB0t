package com.p3achb0t.api

import com.p3achb0t._runestar_interfaces.Obj
import com.p3achb0t._runestar_interfaces.Projectile
import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.GroundItem
import com.p3achb0t.api.wrappers.NPC
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo
import java.awt.Point
import kotlin.math.abs
import kotlin.math.max

class Projectiles(val ctx: Context) {


    fun getProjectile(): Projectile {
        var projectile = null
        var proj = ctx.client.getProjectiles()
        var obj = proj.getSentinel()

        if (obj != null)
            obj = obj.getPrevious()
        if (obj is Projectile) {
            try {
                val speedx = obj.getSpeedX()
                val speedy = obj.getSpeedY()
                val acceleration = obj.getAccelerationZ()
                val id = obj.getId()
                val targetindex = obj.getTargetIndex()
                val getx = obj.getX()
                val gety = obj.getY()
                val getz = obj.getZ()
                val proj = com.p3achb0t.api.wrappers.Projectile(
                        ctx,
                        id,
                        ObjectPositionInfo(getx.toInt(), gety.toInt(),getz.toInt()),
                        speedx,
                        speedy,
                        acceleration,
                        targetindex,
                        getx,
                        gety,
                        getz
                        
                )

            } catch (e: Exception) {
                println(e.stackTrace)
            }

        }

        return proj
    }

}
