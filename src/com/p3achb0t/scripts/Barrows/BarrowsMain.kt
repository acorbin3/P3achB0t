package com.p3achb0t.scripts.Barrows

import TraverseDragons
import com.naturalmouse.api.MouseMotionFactory
import com.p3achb0t.UserDetails
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.Context
import com.p3achb0t.api.LoggingIntoAccount
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.scripts.RuneDragons.Bank
import com.p3achb0t.scripts.Task
import doCombat
import kotlinx.coroutines.delay
import org.apache.commons.lang.time.StopWatch
import java.awt.Color
import java.awt.Graphics


@ScriptManifest("BarrowsMain","BarrowsMain","Zak")
class BarrowsMain : AbstractScript() {
    var factory = MouseMotionFactory()
    val stopwatch = StopWatch()
    var currentJob = ""

    //var playerss = ctx.getPlayers() GIVES an error

    override suspend fun loop() {

        run()
        delay((Math.random() * 50).toLong())

    }

    override suspend fun start() {
        try {
            if (ctx.client.getGameState() == 30){
                UserDetails.data.username = ctx.client.getLogin_username()
                UserDetails.data.password = ctx.client.getLogin_password()
            }
            stopwatch.start()
            Antifiretimer.start()
            Divinepottimer.start()
            IdleTimer.start()
        } catch (e: Exception) {
        }
        println("Running Start")
        LoggingIntoAccount(ctx)
        //Lets wait till the client is logged in
        while (ctx.client.getGameState() != 30) {
            delay(100)
        }
    }

    override fun stop() {
        println("Stopping Test script")
        stopwatch.reset()
        Antifiretimer.reset()
        Divinepottimer.reset()
        IdleTimer.reset()
    }

    override fun draw(g: Graphics) {
        g.color = Color.WHITE
        g.drawString("Current Runtime: $stopwatch", 12, 400)
        g.drawString(currentJob, 12, 415)
        super.draw(g)
    }

    var isInititilized = false
    val Task = ArrayList<Task>()
    fun init() {
        Task.add(Bank(ctx))
        Task.add(doCombat(ctx))
        Task.add(TraverseDragons(ctx))
        isInititilized = true
    }

    suspend fun run() {

        if (ctx.widgets.isWidgetAvaliable(413, 77)) {
            val welcomeScreen = WidgetItem(ctx.widgets.find(413, 77), ctx = ctx)
            welcomeScreen.click()
        }
        if(ctx.client.getGameState() == 10){
            println("Logging in")
            LoggingIntoAccount(ctx)
            //Lets wait till the client is logged in
                delay(3000)
        }
        if (!isInititilized) init()
//        if (!LoggingIntoClient.loggedIn) return

        Task.forEach {
            if (it.isValidToRun()) {

                currentJob = it.javaClass.name
                it.execute()

            }
        }
    }

    companion object {
        var Antifiretimer = StopWatch()
        var Divinepottimer = StopWatch()
        var IdleTimer = StopWatch()
        fun shouldBank(ctx: Context): Boolean {
            val combatArea = Area(
                    Tile(1575, 5086, ctx = ctx),
                    Tile(1597, 5062, ctx = ctx), ctx = ctx
            )
            var cwBank = Tile(2442, 3083, 0 , ctx=ctx)
            return !ctx.inventory.hasDivineCombats() && cwBank.distanceTo() < 30 || !ctx.inventory.hasDueling() || (!ctx.inventory.Contains(385) && ctx.players.getLocal().getHealth() > 50) || (!ctx.inventory.hasPrayerPots() && ctx.players.getLocal().getPrayer() <= 1) ||
                    (!ctx.inventory.hasPendant() && ctx.npcs.findNpc("Rune dragon").size == 0 && cwBank.distanceTo() < 30) || (!ctx.inventory.hasextendedSuperAntiFire() && ctx.npcs.findNpc("Rune dragon").size == 0) ||
                    (cwBank.distanceTo() < 30 && (ctx.inventory.getCount(385) < 1 || ctx.inventory.getPrayerDoses() < 1) || (combatArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation()) && !canFightNext(ctx)))
        }
        fun canFightNext(ctx: Context): Boolean {
            return ctx.npcs.findNpc("Rune dragon").size > 0 && ctx.inventory.getCount(385) > 0 && ctx.inventory.getPrayerDoses() > 0
        }
    }




}