package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.ScriptManifest
import java.awt.Graphics

@ScriptManifest("TestScript","BankPin","P3aches")
class TestBankPin : AbstractScript() {
    var state = 0


    override suspend fun loop() {

        if(!ctx.bank.isOpen()){
            ctx.bank.open()
        }

        Thread.sleep(500)

    }

    override suspend fun start() {
        println("Starting Test Bank Pin")
        state = ctx.client.getGameState() // this works
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics) {
    }
}