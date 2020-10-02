package com.p3achb0t.scripts.service.restart_action

import com.p3achb0t.api.StopWatch
import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.script.ServiceScript
import com.p3achb0t.api.utils.Script
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.client.accounts.Account
import com.p3achb0t.scripts.paint.debug.DebugText
import java.awt.Color
import java.awt.Font
import java.awt.Graphics

@ScriptManifest(Script.SERVICE, "RestartIdle", "P3aches", "0.1")
class RestartIdle : ServiceScript() {
    var initilized = false
    val idleStopWatch = StopWatch()
    val currentTile = Tile()
    var shouldRestart = false
    val idleThresholdInMin = 10

    override suspend fun loop(account: Account) {
        if(!ctx.worldHop.isLoggedIn
                || ctx.worldHop.isWelcomeRedButtonAvailable()
                || (ctx.worldHop.isLoggedIn
                        && (currentTile.x != ctx.players.getLocal().getGlobalLocation().x
                        || currentTile.y != ctx.players.getLocal().getGlobalLocation().y))
        ){
            idleStopWatch.reset()
            if(ctx.worldHop.isLoggedIn) {
                currentTile.x = ctx.players.getLocal().getGlobalLocation().x
                currentTile.y = ctx.players.getLocal().getGlobalLocation().y
            }
        }

        if(ctx.worldHop.isLoggedIn
                && idleStopWatch.elapsedMin >= idleThresholdInMin){
            shouldRestart = true
        }

    }

    override fun draw(g: Graphics) {

        val debugText = arrayListOf<DebugText>()
        debugText.add(DebugText("Idle time: ${idleStopWatch.getRuntimeString()}"))
        if(ctx.worldHop.isLoggedIn) {
            debugText.add(DebugText("saved tile: $currentTile "))
            debugText.add(DebugText("Player tile: ${ctx.players.getLocal().getGlobalLocation()}"))
        }else{
            idleStopWatch.reset()
        }
        val x = 10
        var y = 150
        g.font = Font("Consolas", Font.PLAIN, 12)
        debugText.forEach {
            g.color = it.color
            g.color = Color.green
            g.drawString(it.text, x, y)
            y += 13
        }

        super.draw(g)
    }
}