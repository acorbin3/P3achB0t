package com.p3achb0t.api

import com.naturalmouse.custom.RuneScapeFactoryTemplates
import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.GroundItems
import com.p3achb0t.api.wrappers.NPCs
import com.p3achb0t.api.wrappers.Players
import com.p3achb0t.client.ui.components.GameTab
import com.p3achb0t.interfaces.IScriptManager
import java.applet.Applet
import java.awt.Graphics

abstract class AbstractScript {

    protected lateinit var ctx: Context
    protected lateinit var players: Players
    protected lateinit var groundItems: GroundItems
    protected lateinit var npcs: NPCs
    protected lateinit var mouse: Mouse
    protected lateinit var keyboard: Keyboard

    fun initialize(client: Any) {
        val script = client as IScriptManager
        val applet = client as Applet
        val factory = RuneScapeFactoryTemplates.createAverageComputerUserMotionFactory(client)
        mouse = Mouse(applet.getComponent(0),factory,script.getMouse())
        //keyboard = Keyboard(client)
        //ctx = Context(client, mouse, keyboard, gameTab)
        players = Players(ctx)
        groundItems = GroundItems(ctx)
        npcs = NPCs(ctx)
    }

    abstract suspend fun loop()

    abstract suspend fun start()

    abstract suspend fun stop()

    open fun draw(g: Graphics) {}
}