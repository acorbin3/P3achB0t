package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
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

    suspend fun deactivateRun() {
        if (isRunActivated()) clickRunButton()
    }

    //1065 is activated, 1064 is not activated
    fun isRunActivated(): Boolean {
        return ctx.widgets.find(PARENT, CHILD_RUN_ENERGY_NUMBER)?.getSpriteId2() == 1065
    }
}