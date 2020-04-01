package com.p3achb0t.client.test

import com.p3achb0t.api.DebugScript
import java.awt.Color
import java.awt.Graphics
import kotlin.random.Random

class ComScript2 : DebugScript() {

    var isSub = false
    var r = Random.nextInt(1000)
    var m = ""

    override fun draw(g: Graphics) {
        if (!isSub) {
            //ctx.communication.subscribe("1234")
            ctx.ipc.subscribe(ctx.ipc.uuid, ::callback)
            isSub = true
        }

        //ctx.ipc.send("42", "id: $r, ComScript2")
        g.color = Color.CYAN
        g.drawString("Channel id ${ctx.ipc.uuid}", 50, 100)
        g.drawString("Script uuid ${ctx.ipc.scriptUUID}", 50,120)
        g.drawString(m, 50, 140)
    }

    override fun start() {
    }

    override fun stop() {

    }

    /*
    override fun draw(g: Graphics) {
        g.color = Color.CYAN
        g.drawString("Received: $m",50,50)
        g.drawString("Own id: $r", 50, 65)
    }*/


    private fun callback(id: String, message: String) {
        if (message.contains(ctx.ipc.scriptUUID)) {

        } else {
            m = message
            ctx.ipc.send(ctx.ipc.uuid, "callback from ${ctx.ipc.scriptUUID})")
            //println("received from [ room: $id, $message ]")
        }
    }
}