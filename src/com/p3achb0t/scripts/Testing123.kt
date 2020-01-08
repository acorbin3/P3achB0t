package com.p3achb0t.scripts

import com.naturalmouse.api.MouseMotion
import com.p3achb0t.api.*
import com.p3achb0t.api.wrappers.*
import com.p3achb0t.api.wrappers.tabs.Equipment
import com.p3achb0t.api.wrappers.tabs.Inventory
import com.p3achb0t.api.wrappers.tabs.Magic
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import org.apache.commons.lang.time.StopWatch
import java.awt.Color
import java.awt.Graphics
import kotlin.random.Random

@ScriptManifest("Testing123","Testing123","Zak")
class Testing123 : AbstractScript() {
    val stopwatch = StopWatch()
    var currentJob = ""
    //var playerss = ctx.getPlayers() GIVES an error
    override suspend fun loop() {

        run()
        delay((Math.random() * 50).toLong())

    }

    override suspend fun start() {
        try {
            stopwatch.start()
        } catch (e: Exception) {
        }

    }

    override fun stop() {
        println("Stopping Test script")
        stopwatch.reset()

    }

    override fun draw(g: Graphics) {
        g.color = Color.black
        g.drawString("Current Runtime: $stopwatch", 10, 450)
        g.drawString(currentJob, 10, 460)
        super.draw(g)
    }

    var isInititilized = false
    val jobs = ArrayList<Job>()
    fun init() {
        jobs.add(MoveMouse(ctx))
        isInititilized = true
    }

    suspend fun run() {
        if (!isInititilized) init()
//        if (!LoggingIntoClient.loggedIn) return
        jobs.forEach {
                currentJob = it.javaClass.name
                it.execute()
            }
    }

    class MoveMouse(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return true
        }

        override suspend fun execute() {
            val loot: IntArray = intArrayOf(2363, 1127, 1079, 1303, 1347, 4087, 4180, 4585, 1149, 892, 21880, 562, 560, 212, 208, 3052, 220, 19580, 9381, 1616, 452, 19582,
                    21930, 995, 21918, 22103, 11286, 1333, 536)
            val groundloot = ctx.groundItems.getItempred(loot)
            if (!groundloot.isEmpty()) {

                groundloot.forEach {
                    println("item found " + it.id)
                    if (ctx.inventory.isFull()) {
                        ctx.inventory.getItem(385)?.click()
                        delay(400)
                    }
                    if (!ctx.inventory.isFull()) {
                        try {
                            if (!it.isOnScreen()) {
                                if (it.distanceTo() > 5) {
                                    it.clickOnMiniMap()
                                    delay(800)
                                }
                                it.turnTo()
                                Utils.waitFor(3, object : Utils.Condition {
                                    override suspend fun accept(): Boolean {
                                        delay(100)
                                        return it.isOnScreen()
                                    }
                                })
                            }
                            if (it.isOnScreen()) {
                                it.interact("Take")
                            }


                        } catch (e: Exception) {
                            println("Error: NPC " + e.message)
                            e.stackTrace.iterator().forEach {
                                println(it)
                            }
                        }
                    }
                }
            }
        }
    }

}