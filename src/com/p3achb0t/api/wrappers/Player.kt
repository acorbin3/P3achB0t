package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.getConvexHull
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import java.awt.Point


class Player(var player: com.p3achb0t.api.interfaces.Player, ctx: Context, val menuIndex: Int) : Actor(player, ctx) {
    companion object {
        val PARENT = 160
        val CHILD_SPECIAL_ATTACK_BUTTON = 30
        val CHILD_SPECIAL_ATTACK_NUMBER = 31
    }

    val cleanUsername: String get(){
        return player.getUsername().getCleanName()
    }
    val isSpecialEnabled: Boolean
    get(){
        return ctx?.vars?.getVarp(301) == 1
    }
    val specialAttackNumber: Int
        get() {
            return ctx?.widgets?.find(PARENT, CHILD_SPECIAL_ATTACK_NUMBER)?.getText()?.toInt() ?: 0
        }

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



    fun getCombatStyle(): Int {
        return try {
            return ctx!!.vars.getVarbit(43)
        } catch (e: Exception) {
            println("getCombatStyle threw an exception")
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