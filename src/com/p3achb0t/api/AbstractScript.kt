package com.p3achb0t.api

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.newApi.Players
import com.p3achb0t.api.wrappers.GroundItems
import com.p3achb0t.api.wrappers.NPCs
import com.p3achb0t.client.ui.components.TabManager
import java.awt.Graphics

abstract class AbstractScript {
    protected val ctx: Client
    protected val players: Players
    protected val groundItems: GroundItems
    protected val npcs: NPCs

    init {
        val c = TabManager.instance.getSelected()
        ctx = c.client!!.client!!
        players = Players(ctx)
        groundItems = GroundItems(ctx)
        npcs = NPCs(ctx)
    }

    fun getClient(): Client {
        return ctx
    }

    abstract suspend fun loop()

    abstract suspend fun start()

    abstract suspend fun stop()

    open fun draw(g: Graphics) {}
}