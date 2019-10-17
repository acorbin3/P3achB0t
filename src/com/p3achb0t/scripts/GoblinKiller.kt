package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.ScriptManifest
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

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics) {
    }
}