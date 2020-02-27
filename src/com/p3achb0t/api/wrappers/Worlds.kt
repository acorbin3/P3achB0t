package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context

class Worlds(val ctx: Context) {

    fun getWorlds(){
        ctx.client.getWorlds()
    }

}
