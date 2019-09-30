package com.p3achb0t.api

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.newApi.Players
import com.p3achb0t.api.wrappers.GroundItems
import com.p3achb0t.api.wrappers.NPCs
import com.p3achb0t.interfaces.IScriptManager
import java.awt.Graphics

abstract class AbstractScript {

    protected lateinit var ctx: Client
    protected lateinit var players: Players
    protected lateinit var groundItems: GroundItems
    protected lateinit var npcs: NPCs


    fun initialize(ctx: Client) {
        this.ctx = ctx
        players = Players(ctx)
        groundItems = GroundItems(ctx)
        npcs = NPCs(ctx)
    }

    abstract fun loop()

    abstract fun start()

    abstract fun stop()

    open fun draw(g: Graphics) {}
}