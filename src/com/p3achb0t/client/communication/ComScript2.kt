package com.p3achb0t.client.communication

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.ChannelInterface1
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics
import kotlin.random.Random

class ComScript2 : AbstractScript(), ChannelInterface1 {

    var isSub = false
    var r = Random.nextInt(1000)
    var m = ""
    override suspend fun loop() {
        if (!isSub) {
            ctx.communication.subscribe("1234")
            isSub = true
        }
        delay(200)
        ctx.communication.send1("1234", "id: $r, ComScript2")
    }

    override suspend fun start() {
    }

    override fun stop() {

    }

    override fun draw(g: Graphics) {
        g.color = Color.CYAN
        g.drawString("Received: $m",50,50)
        g.drawString("Own id: $r", 50, 65)
    }

    override fun receive(id: String, message: String) {

        if (message.contains("$r")) {

        } else {
            m = message
            //println("received from [ room: $id, $message ]")
        }


    }
}