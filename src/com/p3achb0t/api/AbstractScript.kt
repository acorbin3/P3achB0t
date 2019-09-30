package com.p3achb0t.api

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.newApi.Players
import com.p3achb0t.api.wrappers.GroundItems
import com.p3achb0t.ui.components.TabManager
import java.awt.Graphics

abstract class AbstractScript {

    protected lateinit var client: Client
    protected lateinit var ctx: Context
    protected lateinit var players: Players
    protected lateinit var groundItems: GroundItems
    protected valateinit varl npcs: NPCs
    protected lateinit var mouse: Mouse
    protected lateinit var keyboard: Keyboard

    fun initialize(ctx: Client) {
        this.ctx = ctx
        players = Players(ctx)
        groundItems = GroundItems(ctx)
        npcs = NPCs(ctx)

        val c = TabManager.instance.getSelected()
        client = c.client!!.client!!
        players = Players(c.ctx)
        groundItems = GroundItems(c.ctx)
        npcs = NPCs(c.ctx)
        mouse = c.mouse
        keyboard = c.keyboard
        ctx = c.ctx
    }

    abstract suspend fun loop()

    abstract suspend fun start()

    abstract suspend fun stop()

    open fun draw(g: Graphics) {}
}