package com.p3achb0t.api.wrappers

import com.p3achb0t.MainApplet
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.interfaces.Locatable
import com.p3achb0t.hook_interfaces.HealthBar
import com.p3achb0t.hook_interfaces.HealthBarData
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics2D
import kotlin.random.Random


open class Actor(var raw: com.p3achb0t.hook_interfaces.Actor) : Locatable {


    fun isIdle(): Boolean {
        return raw.getAnimation() == -1 && raw.getInteracting() == -1 && raw.getRuntimeAnimation() == 808
    }

    suspend fun waitTillIdle(time: Int = 4) {
        //Add a small delay to allow for initial movement from the previous command
        delay(Random.nextLong(100, 400))
        Utils.waitFor(time, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return Players.getLocal().isIdle()
            }
        })
    }

    //TODO - fix getting health
    fun getHealth(): Int {
        println("Looking for healthbars")
        var node = raw.getHealthBars().getNode()
        if (raw.getHealthBars().getNode() is HealthBar) {
            println("Found healthbar")
        }
        if (node == null) {
            println("Node is null")
        }

        var count = 0
        while (node != null) {
            println("Looking at node $count")
            if (node is HealthBar) {
                println("Found health Bar")
                var healthData = node.getData().getNode()
                while (healthData != null && healthData != node.getData()) {
                    if (healthData is HealthBarData) {
                        println("Found Health bar data")
                        return healthData.getCurrentHealth()
                    }
                    healthData = healthData.getNext()
                }

            }
            count += 1
            node = node.getNext()
        }
        return 0
    }
    override fun isOnScreen(): Boolean {
        val tilePoly = Calculations.getCanvasTileAreaPoly(
            raw.getLocalX(),
            raw.getLocalY()
        )
        return Calculations.isOnscreen(tilePoly.bounds)
    }


    override fun draw(g: Graphics2D) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics2D, color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGlobalLocation(): Tile {
        return Tile(
            raw.getLocalX() / 128 + MainApplet.clientData.getBaseX(),
            raw.getLocalY() / 128 + MainApplet.clientData.getBaseY(),
            MainApplet.clientData.getPlane()
        )
    }

}