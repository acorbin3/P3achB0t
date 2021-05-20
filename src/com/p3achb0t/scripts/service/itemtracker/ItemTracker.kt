package com.p3achb0t.scripts.service.itemtracker

import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.script.ServiceScript
import com.p3achb0t.api.utils.Script
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.client.accounts.Account
import com.p3achb0t.scripts.paint.debug.DebugText
import java.awt.Color
import java.awt.Font
import java.awt.Graphics

@ScriptManifest(Script.SERVICE, "XP Tracker", "P3aches", "0.1")
class ItemTracker : ServiceScript() {
    var initilized = false
    override suspend fun loop(account: Account) {
        ctx.inventory.updateTrackedItems()

    }


    fun checkInventoryCounts(){
        //If bank is opened and items removed, reset the current values
    }



    override fun draw(g: Graphics) {
        val debugText = arrayListOf<DebugText>()
        Stats.Skill.values().iterator().forEach {
            if (ctx.stats.xpGained(it) > 0) {
                val name = it.name.padEnd(11, ' ')
                debugText.add(DebugText(name
                        + " XP: " + ctx.stats.xpGained(it).toString().padStart(7)
                        + " (+${ctx.stats.levelsGained(it).toString().padEnd(2)}) "
                        + "TTL(${ctx.stats.level(it).toString().padStart(2)}->${(ctx.stats.level(it) + 1).toString().padStart(2)}) "
                        + ctx.stats.timeTillNextLevel(it)
                        + ctx.stats.xpPerHourFormatted(it).padStart(7) + " xp/h "))
            }
        }
        val x = 10
        var y = 40
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