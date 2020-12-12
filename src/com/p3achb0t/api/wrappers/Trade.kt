package com.p3achb0t.api.wrappers

import com.p3achb0t.api.interfaces.Component
import com.p3achb0t.api.Context
import com.p3achb0t.api.userinputs.DoActionParams
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import kotlin.random.Random

class Trade(val ctx: Context) {

    val PARENT_OFFER = 335
    val CHILD_YOUR_OFFER = 25
    val CHILD_THEIR_OFFER = 28
    // look at item_id

    fun doesYourOffContain(ids: IntArray):Boolean{
        val items = getYourOfferItems()
        return areAllItemsFound(items, ids)
    }


    fun doesTheirOfferContain(ids: IntArray): Boolean{
        val items = getTheirOfferItems()
        return areAllItemsFound(items, ids)
    }

    // This function will loop over the offer making sure all items are in
    // the trade window
    private fun areAllItemsFound(items: ArrayList<WidgetItem>, ids: IntArray): Boolean {
        var matchingItemCount = 0
        items.forEach {
            if (it.id in ids) {
                matchingItemCount += 1
            }
        }
        return matchingItemCount == ids.size
    }

    fun getYourOfferItems(): ArrayList<WidgetItem>{
        val childID = CHILD_YOUR_OFFER
        return getOfferItems(childID)
    }

    fun getTheirOfferItems(): ArrayList<WidgetItem>{
        val childID = CHILD_THEIR_OFFER
        return getOfferItems(childID)
    }

    private fun getOfferItems(childID: Int): ArrayList<WidgetItem> {
        val list = ArrayList<WidgetItem>()
        if (isTradeOpen()) {
            val yourItems = ctx.widgets.find(PARENT_OFFER, childID)
            yourItems?.getChildren()?.iterator()?.forEach {
                if (it != null) {
                    if (it.getItemId() > 0) {
                        list.add(WidgetItem(widget = it))
                    }
                }
            }
        }
        return list
    }


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
        return firstTradeWidget() != null && firstTradeWidget()?.getWidth() ?: 0 > 0
    }

    fun isSecondScreenOpen(): Boolean {
        return secondTradeWidget() != null && secondTradeWidget()?.getWidth() ?: 0 > 0
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

    suspend fun offerAll(id: Int) {
        if (isFirstScreenOpen()) {
            var items = ctx.inventory.getAll()
            var index = ctx.inventory.getfirstIndex(id)
            if (!items.isNullOrEmpty()) {
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
    }

    suspend fun offerX(id: Int, count: Int) {
        if (isFirstScreenOpen()) {
            var items = ctx.inventory.getAll()
            var index = ctx.inventory.getfirstIndex(id)
            if (!items.isNullOrEmpty()) {
                out_loop@ for (it in items) {
                    if (it.id == id) {
                        val doActionParams = DoActionParams(index, 22020096, 57, 5, "", "", 0, 0)
                        ctx.mouse.overrideDoActionParams = true
                        ctx.mouse.doAction(doActionParams)
                        Utils.waitFor(3, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                val chatText =
                                        ctx.widgets.find(WidgetID.CHATBOX_GROUP_ID, WidgetID.Chatbox.FULL_INPUT)
                                val text = chatText?.getText()
                                println(text + " " + chatText?.getIsHidden())
                                return text?.equals("*") ?: false
                            }
                        })
                        delay(Random.nextLong(240, 1076))
                        ctx.keyboard.sendKeys(count.toString(), sendReturn = true)
                        delay(Random.nextLong(240, 1076))
                    }

                }
            }
        }
    }


    suspend fun acceptFirstScreen() {
        if (isFirstScreenOpen()) {
            val doActionParams = DoActionParams(-1, 21954570, 57, 1, "", "", 0, 0)
            ctx.mouse.overrideDoActionParams = true
            ctx.mouse.doAction(doActionParams)
        }
    }


    suspend fun acceptSecondScreen() {
        if (isSecondScreenOpen()) {
            val doActionParams = DoActionParams(-1, 21889037, 57, 1, "", "", 0, 0)
            ctx.mouse.overrideDoActionParams = true
            ctx.mouse.doAction(doActionParams)
        }
    }


}
