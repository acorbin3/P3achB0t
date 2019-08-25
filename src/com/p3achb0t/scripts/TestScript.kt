package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.ScriptManifest

import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Test","TestScript","Unoplex")
class TestScript : AbstractScript() {

    var camera = 0

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun loop() {

        camera = ctx.getCameraY()
        Thread.sleep(2000)
    }

    override fun draw(g: Graphics) {
        //g.drawString("Loehde FORCED IT TO WORK", 300, 400)
        clientInfo(g)
    }

    fun clientInfo(g: Graphics) {
        g.color = Color.RED

        g.drawString("Game cycle: ${ctx.getCycle()}", 50,120)
        g.drawString("Game state: ${ctx.getGameState()}", 50,140)
        g.drawString("Login state: ${ctx.getLoginState()}", 50,160)
        g.drawString("Account status: ${ctx.get__cq_aw()}", 50,180)

        g.color = Color.GREEN
        g.drawString("$camera", 50, 210)

        if (ctx.getGameState() == 30) {
            g.color = Color.LIGHT_GRAY
            g.fillRect(8,460,504,14)
        }

    }
}