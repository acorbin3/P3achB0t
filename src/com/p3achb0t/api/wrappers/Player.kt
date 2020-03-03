package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.getConvexHull
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import java.awt.Point


class Player(var player: com.p3achb0t._runestar_interfaces.Player, ctx: Context, val menuIndex: Int) : Actor(player, ctx) {


    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return Calculations.worldToScreen(region.x, region.y, player.getHeight(), ctx!!)
    }

    fun getHealth(): Int {
        return try {
            var healthInt = 0
            val health = WidgetItem(ctx?.widgets?.find(160, 5), ctx = ctx).widget?.getText()
            healthInt = health?.toInt()!!
            return healthInt
        } catch (e: Exception) {
            println("getHealth threw an exception")
            e.stackTrace.iterator().forEach {
                println(it)
            }
            -1
        }
    }



    fun getPrayer(): Int {
        return try {
            var healthInt = 0
            val health = WidgetItem(ctx?.widgets?.find(160, 15), ctx = ctx).widget?.getText()
            healthInt = health?.toInt()!!
            return healthInt
        } catch (e: Exception) {
            println("getPrayer threw an exception")
            e.stackTrace.iterator().forEach {
                println(it)
            }
            -1
        }
    }

    fun getRunEnergy(): Int {
        return try {
            var healthInt = 0
            val health = WidgetItem(ctx?.widgets?.find(160, 23), ctx = ctx).widget?.getText()
            healthInt = health?.toInt()!!
            return healthInt
        } catch (e: Exception) {
            println("getRunEnergy threw an exception")
            e.stackTrace.iterator().forEach {
                println(it)
            }
            -1
        }
    }

    override suspend fun interact(action: String): Boolean {
        //TODO check is player is on screen
        //  TODO - Move camera for player to be on screen
        //TODO - move mouse to player
        //TODO - check if you need to right click for menu or if left click is fine
        //  TODO - if right click interact
        //
        try {
            println("${this.player.getUsername()}: Getting Hull!")
            val ch = getConvexHull(
                    this.player,
                    ctx!!)
            //Checking to see if this is on screen
            Interact(ctx!!).interact(ch, action)
        } catch (e: Exception) {
        }
        return false
    }
}