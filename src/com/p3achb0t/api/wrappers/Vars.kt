package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context

/*
    This class is intended to wrap the following var items. Vars can be used to see states of things within the game
    like birdhouses, rune pouches, minigames, and even quest progress
 */
class Vars(val ctx: Context) {
    /**
     * This method will get the varbit within the client.
     * @exception:There could be a time that this throws an exception. Return back a -1 in that case.
     */
    fun getVarbit(id: Int): Int {
        return try {
            ctx.client.getVarbit(id)
        } catch (e: Exception) {
            println("Varbit threw an exception")
            e.stackTrace.iterator().forEach {
                println(it)
            }
            -1
        }

    }

    fun getVarp(id: Int): Int {
        return try {
            ctx.client.getVarps_main()[id]
        } catch (e: Exception) {
            println("getVarp threw an exception for $id")
            e.stackTrace.iterator().forEach {
                println(it)
            }
            -1
        }
    }

    //    fun getVarcInt(id: Int): Int  { return ctx.client.getVarcs().get }
    fun getVarcString(id: Int): String? {
        return try {
            ctx.client.getVarcs().getStrings()[id]
        } catch (e: Exception) {
            println("getVarcString threw an exception")
            e.stackTrace.iterator().forEach {
                println(it)
            }
            ""
        }
    }
}