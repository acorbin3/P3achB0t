package com.p3achb0t.scripts.action.communication

import com.p3achb0t.api.script.ActionScript
import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.client.communication.sockets.Server
import com.p3achb0t.client.configs.GlobalStructs

@ScriptManifest("Skills", "ScriptIPCServer", "P3aches", "0.1")
class ScriptIPCServer : ActionScript() {
    var server: Server = Server(GlobalStructs.commPort)

    override fun start() {
        super.start()
        ctx.coms.addCallback(::receiveMessage)
    }

    private fun receiveMessage(message: String) {
        println("Callback: $message")
    }
}