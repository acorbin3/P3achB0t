package com.p3achb0t.scripts.service.test

import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.script.ServiceScript
import com.p3achb0t.api.utils.Script

@ScriptManifest(Script.SERVICE,"Test Service","Løhde", "0.1")
class TestService : ServiceScript() {

    val gameWorld = -1
    val password = ""
    val email = ""
    val shouldLogin = false
    val logout = false

    override suspend fun loop() {

    }

    override fun start() {
        ctx.ipc.subscribe(ctx.ipc.uuid, ::ipcCallback)
        ctx.ipc.subscribe("Sexy Channel", ::ipcCallback)
        ctx.ipc.subscribe("Bad Channel", ::ipcCallback)
        ctx.ipc.subscribe("Løhde Channel", ::ipcCallback)
        ctx.ipc.subscribe("Cool kids? Channel", ::ipcCallback)
        ctx.ipc.subscribe("Banned Channel :(", ::ipcCallback)
        ctx.ipc.subscribe("com/github/lohehde/shit/script", ::ipcCallback)
        println("start")
    }

    override fun stop() {
        ctx.ipc.unsubscribe(ctx.ipc.uuid)
        println("stop")
    }

    private fun ipcCallback(channel: String, message: String) {

        // the general channel for all script in the channel
        if (channel == ctx.ipc.uuid) {
            val parts = message.split(";")
            if (parts[0]?.contains("login")) {
                println("login packet")
            }

        }
    }
}

/* States to send



 */