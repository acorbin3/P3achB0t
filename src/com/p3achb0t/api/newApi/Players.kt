package com.p3achb0t.api.newApi

import com.p3achb0t.api.wrappers.Player
import com.p3achb0t.ui.Context

class Players(val ctx: Context) {
    fun getLocal(): Player {
        return Player(ctx.client.getLocalPlayer(), ctx)
    }
}