package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.userinputs.DoActionParams
import com.p3achb0t.api.wrappers.utils.Utils
import kotlinx.coroutines.delay

class WorldHop(val ctx: Context) {
    companion object {
        enum class World(val region: Int) {
            UK(1),
            USA(0),
            AUS(3),
            GER(7),

        }

    }
    val isLoggedIn: Boolean get() { return GameState.currentState(ctx) == GameState.LOGGED_IN}

    val freeWorldNoTotalRequirement = intArrayOf(301, 308, 316, 326, 335, 379, 380, 382, 383, 384, 393, 394, 397, 398, 399, 417, 418, 425, 426, 430, 431,
            433, 434, 435, 436, 437, 438, 439, 440, 451, 452, 453, 454, 455, 456, 457, 458, 459, 469, 470, 471, 472, 473, 474, 475, 476, 497, 498, 499, 500, 591, 502, 503, 504)


    fun getCurrent(): Int{
        var World = 0
        try {
                World = ctx.client.getWorldId()
        } catch (e: Exception) {
            println("World Exception")
        }
        return World
    }


    fun getMembershipDays(): Int{
        return ctx.vars.getVarbit(1780)
    }



    suspend fun logout(){
        this.ctx.mouse.doAction(DoActionParams(-1, 11927560, 57, 1, "Logout", "", 0, 0))
        delay(400)

        Utils.waitFor(10,object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(50)
                return GameState.currentState(ctx) == GameState.LOGIN_SCREEN
            }
        })
        if(GameState.currentState(ctx) == GameState.LOGGED_IN){
            logout()
        }
    }



    fun isMember(): Boolean {
        return ctx.client.getIsMembersWorld()
    }
}