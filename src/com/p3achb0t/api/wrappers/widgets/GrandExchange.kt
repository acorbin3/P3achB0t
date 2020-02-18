package com.p3achb0t.api.wrappers.widgets

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.tabs.Equipment
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import net.runelite.api.MenuOpcode
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.KeyEvent.VK_BACK_SPACE
import kotlin.random.Random

class GrandExchange(val ctx: Context) {
    companion object {
        const val OfferVarpBit = 4439
        enum class Offers(val widgetID: Int, val varpbitValue: Int, val Arg1: Int) {
            OfferSlotOne(7, 1, 30474247),
            OfferSlotTwo(8, 2, 30474248),
            OfferSlotThree(9, 3, 30474249),
            OfferSlotFour(10, 4, 30474250),
            OfferSlotFive(11, 5, 30474251),
            OfferSlotSix(12, 6, 30474252),
            OfferSlotSeven(13, 7, 30474253),
            OfferSlotEight(14, 8, 30474254),

        }
    }

    suspend fun getExchangeWidget(): Component? {
        return ctx.widgets.find(465,1)
    }


    suspend fun isOpen(): Boolean {
        return getExchangeWidget() != null
    }

    suspend fun offerIsOpen(): Boolean {
        return ctx.vars.getVarp(375) != 0
    }

    suspend fun getFirstFreeSlot(): Offers? {
        if(isOpen()) {
           Offers.values().forEach {
               if(!ctx.widgets.find(465, it.widgetID)!!.getChildren()[3].getIsHidden()){
                   return it
               }
           }
        }
        return null
    }



    suspend fun getQuantity(): Int? {
        if(isOpen() && offerIsOpen()) {
            val quantWidget = ctx.widgets.find(465,24)!!.getChildren()[32].getText().filter { it.isDigit() }
            if(quantWidget.length > 0) {
                println("Quantity " + quantWidget.toInt())
                return quantWidget.toInt()
            }
            if(quantWidget.length < 1) {
                return 0
            }
        }
        return null
    }

    suspend fun getPrice(): Int? {
        if(isOpen() && offerIsOpen()) {
            val priceWidget = ctx.widgets.find(465,24)!!.getChildren()[39].getText()
            if(priceWidget.length < 1){
                return 0
            }
            if(priceWidget.length > 0) {
                val priceWidgeonlyNumbers = priceWidget.filter { it.isDigit() }
                println("price = " + priceWidgeonlyNumbers.toInt())
                return priceWidgeonlyNumbers.toInt()
            }
        }
        return null
    }

    suspend fun getItemName(): String? {
        if(isOpen() && offerIsOpen()) {
            val itemName = ctx.widgets.find(465,24)!!.getChildren()[25].getText()
            println("getitemname = " +itemName)
            return itemName
        }
        return null
    }

    suspend fun createBuyOffer(offer: Offers?) {
        if(!offerIsOpen() && offer != null) {
            val doActionParams = DoActionParams(3, offer.Arg1, 57, 1, "", "", 0, 0)
            ctx?.mouse?.overrideDoActionParams = true
            ctx?.mouse?.doAction(doActionParams)
            Utils.waitFor(1, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return offerIsOpen()
                }
            })
            delay(Random.nextLong(50, 200))
        }
        }

    suspend fun buyItem(id: Int, price: Int, quantity: Int){
            if (!offerIsOpen()) {
                createBuyOffer(getFirstFreeSlot())
                delay(Random.nextLong(333, 666))
            }
            if (offerIsOpen()) {
                if(getItemName() != ctx.cache.getItemName(id)){
                    println("Setting item " + ctx.cache.getItemName(id))
                    setItem(id)
                    Utils.waitFor(2, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return getItemName()?.contains(ctx.cache.getItemName(id)) != false
                        }
                    })
                }
                if(getItemName()?.contains(ctx.cache.getItemName(id)) != false) {
                    if (getPrice() != price) {
                        println("Get price = " + getPrice() + " price = " + price)
                        setPrice(price)
                        println("Get price = " + getPrice() + " price = " + price)
                        Utils.waitFor(2, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return getPrice() == price
                            }
                        })
                    }
                    if (getQuantity() != quantity) {
                        println("Get quantity = " + getPrice() + " quantity = " + price)
                        setQuantity(quantity)
                        println("Get quantity = " + getPrice() + " quantity = " + price)
                        Utils.waitFor(2, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return getQuantity() == getQuantity()
                            }
                        })
                    }
                }

                if (getQuantity() == quantity && getPrice() == price && getItemName() == ctx.cache.getItemName(id)){
                    confirm()
                } else goBack()
            }
        }

    suspend fun goBack() {
        if (isOpen() && offerIsOpen()) {
            val doActionParams = DoActionParams(-1, 30474244, 57, 1, "", "", 0, 0)
            println("going back")
            ctx?.mouse?.overrideDoActionParams = true
            ctx?.mouse?.doAction(doActionParams)
            Utils.waitFor(1, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return !offerIsOpen()
                }
            })
            delay(Random.nextLong(189, 444))
        }
    }

    suspend fun setPrice(price: Int) {
        val chatText =
                ctx.widgets.find(WidgetID.CHATBOX_GROUP_ID, 44)
        while(getPrice() != null && getPrice() != price && offerIsOpen()) {
            val text = chatText?.getText()
            val doActionParams = DoActionParams(12, 30474264, 57, 1, "", "", 0, 0)
            ctx?.mouse?.overrideDoActionParams = true
            ctx?.mouse?.doAction(doActionParams)
            Utils.waitFor(1, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    println(text + " price: does text contain?" + text?.contains("Set a price"))
                    return text?.contains("Set a price") ?: true
                }
            })
            delay(Random.nextLong(155, 333))
            println("setting price in SET price" + price)
            if(text?.contains("Set a price") ?: true) {
                ctx.keyboard.sendKeys(price.toString(), true, true)
            }
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return return getPrice() == price
                }
            })
            delay(Random.nextLong(222, 444))
        }
    }

    suspend fun setItem(id: Int) {
        val chatText =
                ctx.widgets.find(WidgetID.CHATBOX_GROUP_ID,53)
        if(chatText != null) {
                Utils.waitFor(2, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        val chatText =
                                ctx.widgets.find(162, 45)
                        val text = chatText?.getText()
                        println(text + " " + chatText?.getIsHidden())
                        return text?.contains("*") ?: false
                    }
                })
                var searchtext =  ctx.widgets.find(162, 45)

                if(searchtext != null) {
                    if (!searchtext.getText().contains(ctx.cache.getItemName(id))) {
                        ctx.keyboard.sendKeys(ctx.cache.getItemName(id), true, true)
                        delay(Random.nextLong(222, 444))
                    }
                        if (searchtext.getText().length > 46) {
                            var finaltext = searchtext.getText().substring(46, searchtext.getText().length - 1)
                            println("Final text = " + finaltext)
                            if (finaltext.contentEquals(ctx.cache.getItemName(id))) {
                                println("final text true")
                                delay(Random.nextLong(50, 200))
                                if(ctx.cache.getItemName(id).equals("Shark")){
                                    val doActionParams = DoActionParams(3, 10616885, 57, 1, "", "", 0, 0)
                                    ctx?.mouse?.overrideDoActionParams = true
                                    ctx?.mouse?.doAction(doActionParams)
                                    delay(Random.nextLong(155, 333))
                                }
                                if(!ctx.cache.getItemName(id).equals("Shark")) {
                                    val doActionParams = DoActionParams(0, 10616885, 57, 1, "", "", 0, 0)
                                    ctx?.mouse?.overrideDoActionParams = true
                                    ctx?.mouse?.doAction(doActionParams)
                                    delay(Random.nextLong(155, 333))
                                }

                            }
                        }
                }
        }
    }

    suspend fun setQuantity(qauntity: Int) {
        println("setting quantity")
        val chatText =
                ctx.widgets.find(WidgetID.CHATBOX_GROUP_ID,44)
        while(getQuantity() != null && getQuantity() != qauntity && offerIsOpen()) {
            val text = chatText?.getText()
            val doActionParams = DoActionParams(7, 30474264, 57, 1, "", "", 0, 0)
            ctx?.mouse?.overrideDoActionParams = true
            ctx?.mouse?.doAction(doActionParams)
            Utils.waitFor(1, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)

                    println(text + " quantity: does text contains?" + text?.contains("How many do"))
                    return text?.contains("How many do") ?: true
                }
            })
            delay(Random.nextLong(155, 333))
            if(text?.contains("How many do") ?: true) {
                ctx.keyboard.sendKeys(qauntity.toString(), true, true)
            }
            println("setting quantity " + qauntity)
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return getQuantity() == qauntity
                }
            })
            delay(Random.nextLong(222, 444))
        }
    }


    suspend fun confirm() {
            if (isOpen() && offerIsOpen()) {
                val doActionParams = DoActionParams(-1, 30474267, 57, 1, "", "", 0, 0)
                println("Confirming")
                ctx?.mouse?.overrideDoActionParams = true
                ctx?.mouse?.doAction(doActionParams)
                Utils.waitFor(1, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return !offerIsOpen()
                    }
                })
                delay(Random.nextLong(189, 444))
            }
    }

    suspend fun open() {
        if (!isOpen()) {
            val GE = ctx.gameObjects.find(10061)
                GE[0].doAction()
                Utils.waitFor(5, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return isOpen()
                    }
                })
            delay(Random.nextLong(189, 444))
        }
    }

    suspend fun collectAll() {
        if(isOpen()) {
            val doActionParams = DoActionParams(0, 30474246, 57, 1, "", "", 0, 0)
            ctx?.mouse?.overrideDoActionParams = true
            ctx?.mouse?.doAction(doActionParams)
            delay(Random.nextLong(222, 555))
        }
    }

    suspend fun sellItem(id: Int, price: Int) {
        if(isOpen()) {
            if(!offerIsOpen()){
                println("offer is not open")
                sell(id)
                Utils.waitFor(1, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return offerIsOpen()
                    }
                })
                delay(Random.nextLong(50, 200))
            }
            if (getPrice() != price) {
                println("Setting price " + price)
                setPrice(price)
                Utils.waitFor(2, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return getPrice() == price
                    }
                })
            }
                if(getPrice() == price){
                    delay(Random.nextLong(189, 444))
                    confirm()
                }
        }
    }

    suspend fun closeGE() {
        if(isOpen()) {
            val doActionParams = DoActionParams(11, 30474242, 57, 1, "", "", 0, 0)
            ctx?.mouse?.overrideDoActionParams = true
            ctx?.mouse?.doAction(doActionParams)
            delay(Random.nextLong(189, 444))
        }
    }

    suspend fun sell(id: Int) {
        var items = getAll()
        var index = getfirstIndex(id)
        out_loop@ for (it in items) {
            println("Selling item: " +id + " it = " + it.id)
            if (it.id == id) {
                val doActionParams = DoActionParams(index, 30605312, 57, 1, "", "", 0, 0)
                ctx?.mouse?.overrideDoActionParams = true
                ctx?.mouse?.doAction(doActionParams)
                delay(600)
                break@out_loop
            }
        }
    }

    fun getAll(): ArrayList<WidgetItem> {
        val items = ArrayList<WidgetItem>()
        val inventory = ctx.inventory.getWidget()
        // Weird hack check to ensure inventory widget has correct x position. On logon I have seen it return zero
        if (inventory != null && Widget.getDrawableRect(inventory, ctx!!).x > 0) {
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
