package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import kotlin.random.Random

class Run(val ctx: Context) {
    companion object {
        val PARENT = 160
        val CHILD_BUTTON = 22
        val CHILD_RUN_ENERGY_NUMBER = 23
        val CHILD_RUN_ACTIVATION = 24
    }

    val runEnergy: Int
        get() {
            return ctx.widgets.find(PARENT, CHILD_RUN_ENERGY_NUMBER)?.getText()?.toInt() ?: 0
        }

    suspend fun clickRunButton() {
        WidgetItem(ctx.widgets.find(PARENT, CHILD_BUTTON), ctx = ctx).click()
    }

    suspend fun activateRun() {
            val doActionParams = DoActionParams(-1, 10485782,57, 1, "", "", 0, 0)
            ctx.mouse.overrideDoActionParams = true
        ctx.mouse.doAction(doActionParams)
            delay(Random.nextLong(189, 333))
    }

    suspend fun deactivateRun() {
        if (isRunActivated()) clickRunButton()
    }

    //1065 is activated, 1064 is not activated
    fun isRunActivated(): Boolean {
        return ctx.vars.getVarp(173) == 1
    }
}