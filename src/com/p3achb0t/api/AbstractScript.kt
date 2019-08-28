package com.p3achb0t.api

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.newApi.Players
import com.p3achb0t.ui.components.TabManager
import java.awt.Graphics

abstract class AbstractScript {
    protected val ctx: Client
    protected val players: Players

    init {
        val c = TabManager.instance.getSelected()
        ctx = c.client.client!!
        players = Players(ctx)
    }

    fun getClient(): Client {
        return ctx
    }

    abstract suspend fun loop()

    abstract suspend fun start()

    abstract suspend fun stop()

    open fun draw(g: Graphics) {}
}