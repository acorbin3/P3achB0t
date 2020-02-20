package com.p3achb0t.scripts.BrutalBlackDrags

import com.naturalmouse.api.MouseMotionFactory
import com.p3achb0t.UserDetails
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.Context
import com.p3achb0t.api.LoggingIntoAccount
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.scripts.Task
import kotlinx.coroutines.delay
import org.apache.commons.lang.time.StopWatch
import java.awt.Color
import java.awt.Graphics


@ScriptManifest("BrutalBlackDragsMain","BrutalBlackDragsMain","Zak")
class BrutalBlackDragsMain : AbstractScript() {
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
            Killtimer.start()
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
        Killtimer.reset()
        kills = 0.0
    }

    override fun draw(g: Graphics) {
        g.color = Color.WHITE
        g.drawString("Current Runtime: $stopwatch", 12, 400)
        g.drawString(currentJob, 12, 415)
            g.drawString("Kills(hr): " + kills + "(" + kills / (com.p3achb0t.api.wrappers.utils.Utils.getElapsedSeconds(stopwatch.time) /3600 .toFloat()) + ")", 12, 385)
        g.drawString("Account: " +  UserDetails.data.username, 12, 370)
        super.draw(g)
    }

    var isInititilized = false
    val Task = ArrayList<Task>()
    fun init() {
        Task.add(Bank(ctx))
        Task.add(com.p3achb0t.scripts.BrutalBlackDrags.doCombat(ctx))
        Task.add(TraverseDragons(ctx))
        Task.add(UseCWportal(ctx))
        isInititilized = true
    }

    suspend fun run() {

        if (ctx.widgets.isWidgetAvaliable(413, 77)) {
            val welcomeScreen = WidgetItem(ctx.widgets.find(413, 77), ctx = ctx)
            welcomeScreen.click()
        }
        if (ctx.client.getGameState() == 10) {
            println("Logging in")
            LoggingIntoAccount(ctx)
            //Lets wait till the client is logged in
            delay(3000)
        }
        if (!isInititilized) init()
//        if (!LoggingIntoClient.loggedIn) return

        if (ctx.client.getGameState() == 30) {
            Task.forEach {
                if (it.isValidToRun()) {

                    currentJob = it.javaClass.name
                    it.execute()

                }
            }
        }
    }

    companion object {
        var Antifiretimer = StopWatch()
        var Divinepottimer = StopWatch()
        var IdleTimer = StopWatch()
        var Killtimer = StopWatch()
        var kills = 0.0
        fun shouldBank(ctx: Context): Boolean {
            val combatArea = Area(
                    Tile(1606, 10107, ctx = ctx),
                    Tile(1625, 10086, ctx = ctx), ctx = ctx
            )
            var cwBank = Tile(2442, 3083, 0 , ctx=ctx)
            return !ctx.inventory.hasDivineRange() && cwBank.distanceTo() < 30 || !ctx.inventory.hasDueling() || (!ctx.inventory.Contains(3144) && ctx.players.getLocal().getHealth() < 40) || (!ctx.inventory.hasPrayerPots() && ctx.players.getLocal().getPrayer() <= 1) ||
                    (!ctx.inventory.Contains(13393) && ctx.npcs.findNpc("Brutal black dragon").size == 0 && cwBank.distanceTo() < 30) || (!ctx.inventory.hasextendedAntiFire() && ctx.npcs.findNpc("Brutal black dragon").size == 0) ||
                    (cwBank.distanceTo() < 30 && (ctx.inventory.getCount(3144) < 1 || ctx.inventory.getPrayerDoses() < 1) || (ctx.inventory.getCount(3144) < 1 && ctx.inventory.isFull()))
        }

    }




}