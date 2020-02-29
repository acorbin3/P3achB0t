package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Context

class Trade(val ctx: Context) {

    fun firstTradeWidget(): Component? {
        return ctx.widgets.find(335, 1)
    }

    fun secondTradeWidget(): Component? {
        return ctx.widgets.find(334, 1)
    }

    fun isTradeOpen(): Boolean {
        return firstTradeWidget() != null || secondTradeWidget() != null
    }

    fun isFirstScreenOpen(): Boolean {
        return firstTradeWidget() != null
    }

    fun isSecondScreenOpen(): Boolean {
        return secondTradeWidget() != null
    }


    fun hasOtherPlayerAccepted(): Boolean? {
        return try {
            if (isFirstScreenOpen()) {
                return ctx.widgets.find(335, 30)?.getText()?.contains("Other player has")
            }
            if (isSecondScreenOpen()) {
                return ctx.widgets.find(334, 4)?.getText()?.contains("Other player has")
            }
            return null
        } catch (e: Exception) {
            null
        }
    }

}
