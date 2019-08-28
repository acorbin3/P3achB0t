package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.ScriptManifest
import java.awt.Graphics

@ScriptManifest("","","")
class PrintScript : AbstractScript() {



    override suspend fun loop() {
        println("${ctx.getLoginState()} <---- Loaded PrintScript")
    }

    override suspend fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics) {

    }
}