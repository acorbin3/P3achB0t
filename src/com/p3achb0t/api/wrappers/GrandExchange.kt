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

    suspend fun createBuyOffer(offer: Offers?) {
        if(!offerIsOpen() && offer != null) {
            val doActionParams = DoActionParams(3, offer.Arg1, 57, 1, "", "", 0, 0)
            ctx.mouse.overrideDoActionParams = true
            ctx.mouse.doAction(doActionParams)
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
                    setPrice(price)
                }
                if (getQuantity() != quantity) {
                    setQuantity(quantity)
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
            ctx.mouse.overrideDoActionParams = true
            ctx.mouse.doAction(doActionParams)
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
        run setPrice@{
            if (getPrice() != null && getPrice() != price && offerIsOpen()) {
                println("Offer is open")
                val text = chatText?.getText()
                val doActionParams = DoActionParams(12, 30474264, 57, 1, "", "", 0, 0)
                ctx.mouse.overrideDoActionParams = true
                ctx.mouse.doAction(doActionParams)
                Utils.waitFor(1, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return text?.contains("Set a price") ?: true
                    }
                })
                delay(Random.nextLong(888, 1111))
                if (text?.contains("Set a price") != null) {
                    ctx.keyboard.sendKeys(price.toString(), true, true)
                }
                Utils.waitFor(1, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return return getPrice() == price
                    }
                })
                return@setPrice
            }
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
            run setItem@{
                if(searchtext != null) {
                    if (!searchtext.getText().contains(ctx.cache.getItemName(id))) {
                        delay(Random.nextLong(333, 555))
                        ctx.keyboard.sendKeys(ctx.cache.getItemName(id), true, true)
                        delay(Random.nextLong(222, 444))
                    }
                    if (searchtext.getText().length > 46) {
                        var finaltext = searchtext.getText().substring(46, searchtext.getText().length - 1)
                        if (ctx.cache.getItemName(id).contains(finaltext)) {
                            delay(Random.nextLong(50, 200))
                            if (ctx.cache.getItemName(id).equals("Shark")) {
                                val doActionParams = DoActionParams(3, 10616885, 57, 1, "", "", 0, 0)
                                ctx.mouse.overrideDoActionParams = true
                                ctx.mouse.doAction(doActionParams)
                                delay(Random.nextLong(155, 333))
                            }
                            if (ctx.cache.getItemName(id).equals("Dragon bones")) {
                                val doActionParams = DoActionParams(6, 10616885, 57, 1, "", "", 0, 0)
                                ctx.mouse.overrideDoActionParams = true
                                ctx.mouse.doAction(doActionParams)
                                delay(Random.nextLong(155, 333))
                            }
                            if (!ctx.cache.getItemName(id).equals("Shark") && !ctx.cache.getItemName(id).equals("Dragon bones")) {
                                val doActionParams = DoActionParams(0, 10616885, 57, 1, "", "", 0, 0)
                                ctx.mouse.overrideDoActionParams = true
                                ctx.mouse.doAction(doActionParams)
                                delay(Random.nextLong(155, 333))
                            }

                        }
                    }
                    return@setItem
                }
            }
        }
    }

    suspend fun setQuantity(qauntity: Int) {
        val chatText =
                ctx.widgets.find(WidgetID.CHATBOX_GROUP_ID,44)
        run setQuantity@{
            if (getQuantity() != null && getQuantity() != qauntity && offerIsOpen()) {
                val text = chatText?.getText()
                val doActionParams = DoActionParams(7, 30474264, 57, 1, "", "", 0, 0)
                ctx.mouse.overrideDoActionParams = true
                ctx.mouse.doAction(doActionParams)
                Utils.waitFor(1, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return text?.contains("How many do") ?: true
                    }
                })
                delay(Random.nextLong(222, 444))
                if (text?.contains("How many do") != null) {
                    ctx.keyboard.sendKeys(qauntity.toString(), true, true)
                }
                Utils.waitFor(1, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return getQuantity() == qauntity
                    }
                })
                delay(Random.nextLong(222, 444))
                return@setQuantity
            }
        }
    }


    suspend fun confirm() {
        if (isOpen() && offerIsOpen()) {
            delay(Random.nextLong(111, 222))
            val doActionParams = DoActionParams(-1, 30474267, 57, 1, "", "", 0, 0)
            println("Confirming")
            ctx.mouse.overrideDoActionParams = true
            ctx.mouse.doAction(doActionParams)
            Utils.waitFor(1, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return !offerIsOpen()
                }
            })
            delay(Random.nextLong(111, 222))
        }
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

    suspend fun collectAll() {
        if(isOpen()) {
            val doActionParams = DoActionParams(0, 30474246, 57, 1, "", "", 0, 0)
            ctx.mouse.overrideDoActionParams = true
            ctx.mouse.doAction(doActionParams)
            delay(Random.nextLong(222, 333))
        }
    }

    suspend fun sellItem(id: Int, price: Int) {
        if(isOpen()) {
            if(!offerIsOpen()){
                sell(id)
            }
            if(offerIsOpen()) {
                if (getPrice() != price) {
                    setPrice(price)
                }
                if (getPrice() == price) {
                    confirm()
                }
            }
        }
    }

    suspend fun closeGE() {
        if(isOpen()) {
            val doActionParams = DoActionParams(11, 30474242, 57, 1, "", "", 0, 0)
            ctx.mouse.overrideDoActionParams = true
            ctx.mouse.doAction(doActionParams)
            delay(Random.nextLong(189, 444))
        }
    }

    suspend fun sell(id: Int) {
        var items = getAll()
        var index = getfirstIndex(id)
        out_loop@ for (it in items) {
            if (it.id == id && !offerIsOpen()) {
                val doActionParams = DoActionParams(index, 30605312, 57, 1, "", "", 0, 0)
                ctx.mouse.overrideDoActionParams = true
                ctx.mouse.doAction(doActionParams)
                Utils.waitFor(1, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return offerIsOpen()
                    }
                })
                delay(Random.nextLong(50, 200))
                break@out_loop
            }
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
