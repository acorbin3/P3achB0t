package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import java.awt.Rectangle
import kotlin.random.Random

class Trade(val ctx: Context) {

     fun firstTradeWidget(): Component? {
        return ctx.widgets.find(335,1)
    }

    fun secondTradeWidget(): Component? {
        return ctx.widgets.find(334,1)
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


    fun hasOtherPlayerAccepted(): Boolean?{
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

    suspend fun offerAll(id: Int){
        if(isFirstScreenOpen()) {
            var items = ctx.inventory.getAll()
            var index = ctx.inventory.getfirstIndex(id)
            out_loop@ for (it in items) {
                if (it.id == id) {
                    val doActionParams = DoActionParams(index, 22020096, 57, 4, "", "", 0, 0)
                    ctx.mouse.overrideDoActionParams = true
                    ctx.mouse.doAction(doActionParams)
                    delay(600)
                    break@out_loop
                }
            }
        }
    }

    suspend fun acceptFirstScreen(){
        if(isFirstScreenOpen()) {
            val doActionParams = DoActionParams(-1, 21954570, 57, 1, "", "", 0, 0)
            ctx.mouse.overrideDoActionParams = true
            ctx.mouse.doAction(doActionParams)
        }
    }


    suspend fun acceptSecondScreen(){
        if(isSecondScreenOpen()) {
            val doActionParams = DoActionParams(-1, 21889037, 57, 1, "", "", 0, 0)
            ctx.mouse.overrideDoActionParams = true
            ctx.mouse.doAction(doActionParams)
        }
    }



}
