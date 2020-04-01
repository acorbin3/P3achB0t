package com.p3achb0t.scripts.service.login

import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.ServiceScript
import com.p3achb0t.api.utils.Script

@ScriptManifest(Script.SERVICE,"Login Service","Løhde", "0.1")
class LoginServices : ServiceScript() {

    val gameWorld = -1
    val password = ""
    val email = ""
    val shouldLogin = false
    val logout = false

    override suspend fun loop() {

    }

    override fun start() {
        ctx.ipc.subscribe(ctx.ipc.uuid, ::ipcCallback)
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

            }

        }
    }
}

/* States to send



 */