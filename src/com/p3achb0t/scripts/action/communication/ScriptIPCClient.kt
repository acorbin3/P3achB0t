package com.p3achb0t.scripts.action.communication

import com.p3achb0t.api.Context
import com.p3achb0t.api.script.ActionScript
import com.p3achb0t.api.script.LeafTask
import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.utils.Time.sleep

@ScriptManifest("Skills", "ScriptIPCClient", "P3aches", "0.1")
class ScriptIPCClient : ActionScript() {

    override fun start() {
        super.start()
        tasks.add(SendMessage(ctx))
        ctx.coms.addCallback(::callback)
    }

    class SendMessage(ctx: Context) : LeafTask(ctx) {
        override suspend fun isValidToRun(): Boolean {
            return ctx.worldHop.isLoggedIn
        }

        override suspend fun execute() {
            var list = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

            for (i in list) {
                ctx.coms.sendMessage("Lets do this!!: $i")
                sleep(1000)
            }
        }

    }

    fun callback(message: String) {
        println("Received a message: $message")
    }
}