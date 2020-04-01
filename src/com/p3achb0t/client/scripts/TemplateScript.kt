package com.p3achb0t.client.scripts

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.ScriptManifest
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("category","name","author", "0.1")
class TemplateScript: AbstractScript()  {
    override suspend fun loop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics) {
        g.color = Color.black
        g.drawString("Current Runtime: ", 10, 450)
        super.draw(g)
    }
}