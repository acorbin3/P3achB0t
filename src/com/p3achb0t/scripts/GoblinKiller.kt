package com.p3achb0t.scripts

import com.naturalmouse.api.MouseMotionFactory
import com.naturalmouse.custom.RuneScapeFactoryTemplates
import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.ScriptManifest
import java.awt.Color
import java.awt.Graphics
@ScriptManifest("Fighter","GoblinKiller","Unoplex")
class GoblinKiller : AbstractScript() {
    var state = 0

    //var playerss = ctx.getPlayers() GIVES an error
    override suspend fun loop() {

        Thread.sleep(500)

    }

    override suspend fun start() {
        state = ctx.client.getGameState() // this works
    }

    override suspend fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics) {
    }
}