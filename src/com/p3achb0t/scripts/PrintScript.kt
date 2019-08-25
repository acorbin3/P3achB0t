package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import java.awt.Graphics

class PrintScript : AbstractScript() {

    val clientt = getClient()

    override fun loop() {
        println("${clientt.getLoginState()} <---- Loaded PrintScript")
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics) {

    }
}