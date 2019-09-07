package com.p3achb0t.api

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.newApi.Players
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.GroundItems
import com.p3achb0t.api.wrappers.NPCs
import com.p3achb0t.ui.Context
import com.p3achb0t.ui.components.TabManager
import java.awt.Graphics

abstract class AbstractScript {
    protected val client: Client
    protected val ctx: Context
    protected val players: Players
    protected val groundItems: GroundItems
    protected val npcs: NPCs
    protected val mouse: Mouse
    protected val keyboard: Keyboard

    init {
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