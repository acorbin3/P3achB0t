package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.interfaces.Component
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import java.awt.Rectangle
import kotlin.random.Random

class GrandExchange(val ctx: Context) {
    companion object {
        const val OfferVarpBit = 4439
        enum class Offers(val index: Int, val widgetID: Int, val varpbitValue: Int, val Arg1: Int) {
            OfferSlotOne(0, 7, 1, 30474247),
            OfferSlotTwo(1,8, 2, 30474248),
            OfferSlotThree(2,9, 3, 30474249),
            OfferSlotFour(3,10, 4, 30474250),
            OfferSlotFive(4,11, 5, 30474251),
            OfferSlotSix(5,12, 6, 30474252),
            OfferSlotSeven(6,13, 7, 30474253),
            OfferSlotEight(7,14, 8, 30474254),

        }
    }

    fun getExchangeWidget(): Component? {
        return ctx.widgets.find(465,1)
    }

    fun isCollectButtonAvailable(): Boolean{
        var hasAnyOffers = false
        ctx.client.getGrandExchangeOffers().iterator().forEach {
            if(it.getId() > 0){
                hasAnyOffers = true
            }
        }

        val parentContainer = ctx.widgets.find(465,6)

        return hasAnyOffers
                && parentContainer?.getChildren() != null
                && parentContainer.getChildren().size >= 3
                && parentContainer.getChildren()[1] != null
                && !parentContainer.getChildren()[1].getIsHidden()
    }

    //This function is to return if there is already an offer with a given item ID
    fun isOfferWithItemAlready(itemID: Int): Boolean{
        ctx.client.getGrandExchangeOffers().iterator().forEach {
            if(it.getId() == itemID){
                println("Found GE offer for item: $itemID")
                return true
            }
        }
        print("Did not find offer for item: $itemID")
        return false
    }


    fun isOpen(): Boolean {
        return getExchangeWidget() != null && getExchangeWidget()?.getWidth() ?: 0 > 0
    }

    fun offerIsOpen(): Boolean {
        return ctx.vars.getVarbit(4439) != 0
    }

    fun getFirstFreeSlot(): Offers? {
        if(isOpen()) {
            Offers.values().forEach {
                if(!ctx.widgets.find(465, it.widgetID)!!.getChildren()[3].getIsHidden()){
                    return it
                }
            }
        }
        return null
    }

    fun getOffers(){
        if(isOpen()){
            ctx.client.getGrandExchangeOffers().iterator().forEach {
            }
        }
    }


    fun getQuantity(): Int? {
        if(isOpen() && offerIsOpen()) {
            val quantWidget = ctx.widgets.find(465,24)!!.getChildren()[32].getText().filter { it.isDigit() }
            if(quantWidget.length > 0) {
                return quantWidget.toInt()
            }
            if(quantWidget.length < 1) {
                return 0
            }
        }
        return null
    }

    fun isAnyOfferDoneSelling(): Boolean{
        var isDoneSelling = false

        Offers.values().iterator().forEach {
            if(ctx.client.getGrandExchangeOffers()[it.index] .getState().toInt() == 13){
                isDoneSelling = true

            }
        }
        return isDoneSelling
    }

    fun isAnyOfferDoneBuying(): Boolean{
        var isDoneSelling = false
        Offers.values().iterator().forEach {
            if(ctx.client.getGrandExchangeOffers()[it.index] .getState().toInt() == 5){
                isDoneSelling = true

            }
        }
        return isDoneSelling
    }

    fun isOfferSlotFinishedSelling(offer: Offers): Boolean {
        var isFinished = false
        if (isOpen()) {
            try {
                if (ctx.client.getGrandExchangeOffers()[offer.index].getState().toInt() == 13) {
                    isFinished = true
                }
            } catch (e: Exception) {
                return false
            }
        }
        return isFinished
    }

    fun isOfferSlotFinishedBuying(offer: Offers): Boolean {
        var isFinished = false
        if (isOpen()) {
            try {
                if (ctx.client.getGrandExchangeOffers()[offer.index].getState().toInt() == 5) {
                    isFinished = true
                }
            } catch (e: Exception) {
                return false
            }
        }
        return isFinished
    }

    fun getPrice(): Int? {
        if(isOpen() && offerIsOpen()) {
            val priceWidget = ctx.widgets.find(465,24)!!.getChildren()[39].getText()
            if(priceWidget.length < 1){
                return 0
            }
            if(priceWidget.length > 0) {
                val priceWidgeonlyNumbers = priceWidget.filter { it.isDigit() }
                return priceWidgeonlyNumbers.toInt()
            }
        }
        return null
    }

    fun getItemName(): String? {
        if(isOpen() && offerIsOpen()) {
            val itemName = ctx.widgets.find(465,24)!!.getChildren()[25].getText()
            return itemName
        }
        return null
    }



    suspend fun open() {
        if (!isOpen()) {
            val GE = ctx.gameObjects.find(10061)
            GE[0].click()
            Utils.waitFor(5, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return isOpen()
                }
            })
            delay(Random.nextLong(189, 444))
        }
    }

    fun getAll(): ArrayList<WidgetItem> {
        val items = ArrayList<WidgetItem>()
        val inventory = ctx.inventory.getWidget()
        // Weird hack check to ensure inventory widget has correct x position. On logon I have seen it return zero
        if (inventory != null && Widget.getDrawableRect(inventory, ctx).x > 0) {
            val ids = inventory.getItemIds()
            val stacks = inventory.getItemQuantities()
            val columns = inventory.getWidth()
            val rows = inventory.getHeight()
            val baseX = Widget.getWidgetX(inventory, ctx)
            val baseY = Widget.getWidgetY(inventory, ctx)
            for (i in 0 until (columns * rows)) {
                if (ids[i] > 0 && stacks[i] > 0) {
                    val row = i / columns
                    val col = i % columns
                    val _x = baseX + ((32 + 10) * col)
                    val _y = baseY + ((32 + 4) * row)
                    val area = Rectangle(_x, _y, 32, 32)
                    items.add(
                            WidgetItem(
                                    widget = inventory,
                                    area = area,
                                    id = ids[i] - 1,
                                    stackSize = stacks[i],
                                    type = WidgetItem.Type.INVENTORY,
                                    ctx = ctx
                            )
                    )
                }
            }
        }
        return items
    }

    fun getfirstIndex(id: Int): Int {
        var count = 0
        var index = 0
        var founditem = false
        val inventory = ctx.inventory.getWidget()
        val items = inventory?.getItemIds()
        items?.forEach {
            val ID = it - 1
            if (ID == id && !founditem) {
                println("index = " + count)
                index = count
                founditem = true
            }
            count++
        }
        return index
    }


}