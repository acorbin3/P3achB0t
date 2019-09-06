package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.ScriptManifest

import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Test","TestScript","Unoplex")
class TestScript : AbstractScript() {

    var camera = 0

    override suspend fun start() {
        println("Running Start")
    }

    override suspend fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override suspend fun loop() {

        camera = client.getCameraY()
        Thread.sleep(2000)
    }

    override fun draw(g: Graphics) {
        //g.drawString("Loehde FORCED IT TO WORK", 300, 400)
        clientInfo(g)
    }

    fun clientInfo(g: Graphics) {
        g.color = Color.RED

        g.drawString("Game cycle: ${client.getCycle()}", 50,120)
        g.drawString("Game state: ${client.getGameState()}", 50,140)
        g.drawString("Login state: ${client.getLoginState()}", 50,160)
//        g.drawString("Account status: ${ctx.get__cq_aw()}", 50,180)

        g.color = Color.GREEN
        g.drawString("$camera", 50, 210)

        if (client.getGameState() == 30) {
            g.color = Color.LIGHT_GRAY
            g.fillRect(8,460,504,14)
        }

    }
}