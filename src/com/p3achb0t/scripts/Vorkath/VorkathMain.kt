package com.p3achb0t.scripts

import TraverseDragons
import com.naturalmouse.api.MouseMotionFactory
import com.p3achb0t.UserDetails
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.Context
import com.p3achb0t.api.LoggingIntoAccount
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.wrappers.Actor
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.scripts.RuneDragons.Bank
import com.p3achb0t.scripts.Vorkath.Combat
import doCombat
import kotlinx.coroutines.delay
import org.apache.commons.lang.time.StopWatch
import java.awt.Color
import java.awt.Graphics


@ScriptManifest("Vorkath","Vorkath","Zak")
class VorkathMain : AbstractScript() {
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
            Explosion.start()
            WooxWalk.start()
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
        Explosion.reset()
        WooxWalk.reset()
        Combat.explosion = false
    }

    override fun draw(g: Graphics) {
        g.color = Color.WHITE
        g.drawString("explosion time: $Explosion", 12, 430)
        g.drawString("Current Runtime: $stopwatch", 12, 400)
        g.drawString("Explosion: " + Combat.explosion, 12, 385)
        g.drawString("Explosion proj?: " + Combat.explosionproj, 12, 370)
        g.drawString("Explosion tile: " + Combat.explosiontile, 12, 355)
        g.drawString("my tile: " + ctx.players.getLocal().getGlobalLocation(), 12, 340)
        g.drawString(currentJob, 12, 415)
        super.draw(g)
    }

    var isInititilized = false
    val Task = ArrayList<Task>()
    fun init() {
        Task.add(Combat(ctx))
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
        var Explosion = StopWatch()
        var WooxWalk = StopWatch()

    }




}