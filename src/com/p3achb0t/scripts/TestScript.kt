package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import java.awt.Graphics

class TestScript : AbstractScript() {

    val clientt = getClient()

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun loop() {
        println("${clientt.getCameraY()} <---- Loaded TestScript")
        //players.getLocal()
    }

    override fun draw(g: Graphics) {

    }
}