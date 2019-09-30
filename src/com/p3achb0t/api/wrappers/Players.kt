package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context

class Players(val ctx: Context) {
    fun getLocal(): Player {
        return Player(ctx.client.getLocalPlayer(), ctx)
    }
}