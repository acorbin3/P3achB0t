package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context

/*
    This class is intended to wrap the following var items. Vars can be used to see states of things within the game
    like birdhouses, rune pouches, minigames, and even quest progress
 */
class Vars(val ctx: Context) {
    fun getVarbit(id: Int): Int  {return ctx.client.getVarbit(id)}
    fun getVarp(id: Int): Int  { return ctx.client.getVarps_main()[id] }
//    fun getVarcInt(id: Int): Int  { return ctx.client.getVarcs().get }
    fun getVarcString(id: Int): String? { return ctx.client.getVarcs().getStrings()[id]}
}