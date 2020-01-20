package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context

class Projectiles(val ctx: Context) {

    val projectiles: ArrayList<Projectile> get() {
        val list = ArrayList<Projectile>()
        val sentinel = ctx.client.getProjectiles().getSentinel()
        val cur = sentinel.getPrevious()
        while(cur != null && cur != sentinel){
            if(cur is com.p3achb0t._runestar_interfaces.Projectile){
                list.add(Projectile(ctx,cur))
            }
        }
        return list
    }
}
