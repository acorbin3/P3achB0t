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


    fun isOpen(): Boolean {
        return getExchangeWidget() != null
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


    fun getfirstIndex(id: Int): Int{
        var count = 0
        var index = 0
        var founditem = false
        val inventory = ctx.inventory.getWidget()
        val items = inventory?.getItemIds()
        items?.forEach {
            val ID = it -1
            if(ID == id && !founditem){
                println("index = " + count)
                index = count
                founditem = true
            }
            count++
        }
        return index
    }


}