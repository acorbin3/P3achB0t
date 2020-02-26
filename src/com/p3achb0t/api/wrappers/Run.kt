package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.MenuOpcode
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.widgets.WidgetItem

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
        if (!isRunActivated()) {
            clickRunButton()
        }
    }

    suspend fun activateRunDoAction(){
        if(!isRunActivated()){
            //argument0:-1, argument1:10485782, argument2:57, argument3:1, action:Toggle Run, targetName:, mouseX:641, mouseY:130, argument8:-1223904486
            val doActionParams = DoActionParams(-1, 10485782, MenuOpcode.WIDGET_DEFAULT.id, 1, "", "", 0 ,0)
            ctx.mouse.overrideDoActionParams = true
            ctx.mouse.doAction(doActionParams)
        }
    }

    suspend fun deactivateRun() {
        if (isRunActivated()) clickRunButton()
    }

    //1065 is activated, 1064 is not activated
    fun isRunActivated(): Boolean {
        return ctx.vars.getVarp(173) == 1
    }
}