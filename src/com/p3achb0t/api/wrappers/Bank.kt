package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Context
import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import java.awt.Rectangle
import kotlin.random.Random

class Bank(val ctx: Context) {
    //DONE - open bank
    //DONE - deposit all items
    //TODO - deposit all items from a list
    companion object {
        private val BANK_OBJECTS = intArrayOf(
                782,
                2012,
                2015,
                2213,
                2196,
                4483,
                2453,
                6084,
                11758,
                12759,
                14367,
                19230,
                18491,
                24914,
                25808,
                26972,
                27663,
                29085,
                34752,
                35647,
                36786,
                4483,
                8981,
                14382,
                20607,
                21301,
                24101 // Falador bank booths
        )
    }

    suspend fun open() {

        //First look for bankers, if that doesnt work then look for bank objects
        if (!isOpen()) {
            val bankers = ctx.npcs.findNpc("Banker")
            if (bankers.size > 0) {
                bankers[0].interact("Use" )
                Utils.waitFor(3, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return isOpen() || isPinPanelOpen()
                    }
                })
            }else{
                println("Didnt find any bankers")
            }
        }
        //TODO - interact with bank booths is player is not here


        if(isPinPanelOpen()){
            solveBankPin("1122")//TODO - try to figure out where to get the pin from the account
        }

    }

    suspend fun close() {
        if (isOpen()) {
            // Loop over the children to find the button that can close the bank
            val itemContainer = ctx.widgets.find(WidgetID.BANK_GROUP_ID, WidgetID.Bank.INVENTORY_ITEM_CONTAINER)
            itemContainer?.getChildren()?.iterator()?.forEach {
                val actions = it.getOps()
                actions?.iterator()?.forEach { action ->
                    if (action == "Close") {
                        println("Closing bank")
                        WidgetItem(it, ctx = ctx).click()
                        //Wait till bank is closed
                        Utils.waitFor(3, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return !isOpen()
                            }
                        })
                    }
                }

            }
        }
    }

    suspend fun depositAll() {
        val depositAllWidget = WidgetItem(ctx.widgets.find(WidgetID.BANK_GROUP_ID, WidgetID.Bank.DEPOSIT_INVENTORY), ctx = ctx)
        depositAllWidget.click()
        val inventoryCount = ctx.inventory.getCount()
        Utils.waitFor(3, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return inventoryCount != ctx.inventory.getCount()
            }
        })
    }

    fun itemVisible(itemRect: Rectangle): Boolean {
        return WidgetItem(getBankWidget(), ctx = ctx).area.intersects(itemRect)
    }


    fun getItemCount(id: Int): Int {
        var count = 0
        if (isOpen()) {
            val items = getAll()
            items.forEach {
                if (it.id == id) {
                    count = it.stackSize
                    return@forEach
                }
            }
        }
        return count
    }

    suspend fun withdraw(id: Int, count: Int = 1) {
        if (isOpen()) {
            val items = getAll()
            items.forEach {
                if (it.id == id) {
                    //Check to see if its visible
                    if (itemVisible(it.area)) {
                        val itemCount = getItemCount(id)
                        if (count in listOf(1, 5, 10)) {
                            it.interact("Withdraw-$count" )
                        } else {
                            it.interact("Withdraw-X")
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
                            delay(Random.nextLong(100, 350))
                            ctx.keyboard?.sendKeys(count.toString(), sendReturn = true)
                        }

                        Utils.waitFor(3, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return itemCount != getItemCount(id)
                            }
                        })
                    } else {
                        //TODO- scroll to item
                    }
                }
            }
        }
    }

    suspend fun deposit(id: Int, count: Int = 1) {
        if (isOpen()) {
            val itemCount = getItemCount(id)
            if (count in listOf(1, 5, 10)) {
                ctx.inventory.getItem(id)?.interact("Deposit-$count")
            } else {
                ctx.inventory.getItem(id)?.interact("Deposit-X")
                //Wait till the * shows up in the chat box
                Utils.waitFor(3, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        val chatText = ctx.widgets.find(WidgetID.CHATBOX_GROUP_ID, WidgetID.Chatbox.FULL_INPUT)
                        val text = chatText?.getText()
                        println(text + " " + chatText?.getIsHidden())
                        return text?.equals("*") ?: false
                    }
                })
                delay(Random.nextLong(100, 350))

                ctx.keyboard?.sendKeys(count.toString(), sendReturn = true)
            }
            // wait till item get into the bank
            Utils.waitFor(3, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return itemCount != getItemCount(id)
                }
            })

        }
    }

    fun isOpen(): Boolean {
        return getBankWidget() != null
    }

    fun isPinPanelOpen(): Boolean{
        return getPinPanelWidget() != null
    }

    fun getPinPanelWidget(): Component?{
        return ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID,0)
    }

    fun getBankWidget(): Component? {
        return ctx.widgets.find(WidgetID.BANK_GROUP_ID, WidgetID.Bank.ITEM_CONTAINER)
    }

    fun getSize(): Int {
        if (isOpen()) {
            val widget = ctx.widgets.find(WidgetID.BANK_GROUP_ID, WidgetID.Bank.SIZE)
            if (widget?.getText() != null && widget.getText().trim().isNotEmpty()) {
                return widget.getText().trim().toInt()
            }
        }
        return 0
    }

    fun getAll(): ArrayList<WidgetItem> {
        val itemWidgets = ArrayList<WidgetItem>()
        val bank = getBankWidget()
        var itemCount = 0
        val maxItemCount = getSize()
        bank?.getChildren()?.iterator()?.forEach {

            if (itemCount > maxItemCount) return@forEach

            if (it.getItemId() > 0) {
                itemWidgets.add(
                        WidgetItem(
                                widget = it,
                                id = it.getItemId(),
                                stackSize = it.getItemQuantity(),
                                type = WidgetItem.Type.BANK,
                                ctx = ctx
                        )
                )
            }

            itemCount += 1
        }

        return itemWidgets
    }

    private fun stillSolvingPin(): Boolean{
        val digit1 = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, 3)?.getText()
        val digit2 = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, 4)?.getText()
        val digit3 = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, 5)?.getText()
        val digit4 = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, 6)?.getText()

        return digit1 == "?" || digit2 == "?" || digit3 == "?" || digit4 == "?"
    }

    private suspend fun solveBankPin(pin: String){
        //Check to see if widget 213 is open
        //Pin
        //Look for number in the follow subchild widgets text
        //:(213,16)(1)
        //child : 16,18,20,22,24,26, 28,30,32,34
        //children in the follow can help identify with key we need to press: 3,4,5,6


        while(stillSolvingPin()){
            try {
                //Which one to look for
                val digit1 = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, 3)?.getText()
                val digit2 = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, 4)?.getText()
                val digit3 = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, 5)?.getText()
                val digit4 = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, 6)?.getText()

                when {
                    digit1 == "?" -> {
                        println("Solving digit 1")
                        findAndPressKey(pin[0])
                    }
                    digit2 == "?" -> {
                        println("Solving digit 2")
                        findAndPressKey(pin[1])
                    }
                    digit3 == "?" -> {
                        println("Solving digit 3")
                        findAndPressKey(pin[2])
                    }
                    digit4 == "?" -> {
                        println("Solving digit 4")
                        findAndPressKey(pin[3])
                    }
                }
            }catch (e:Exception){
                //Some cases where we might need to catch errors if the component was not found
            }
        }

    }

    private suspend fun findAndPressKey(digit: Char) {
        var keepSearching = true
        val firstKey = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, WidgetID.BankPinKeys.KEYS[0])!!.getChildren()[1]

        for (keyChildID in WidgetID.BankPinKeys.KEYS) {
            val bankPinKeyWidget = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, keyChildID)
            if (bankPinKeyWidget != null) {
                val children = bankPinKeyWidget.getChildren()
                //Info should be in first index
                children.iterator().forEach {
                    if (it.getText() == digit.toString() && keepSearching) {
                        WidgetItem(it, ctx = ctx).click()
                        //Wait for widget to change
                        Utils.waitFor(2, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                val firstKeyUpdated = ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, WidgetID.BankPinKeys.KEYS[0])!!.getChildren()[1]
                                delay(100)
                                return firstKeyUpdated.getX() == firstKey.getX() && firstKeyUpdated.getY() == firstKey.getY()
                            }
                        })
                        keepSearching = false
                        //Move mouse off the keys
                    }
                }
            }
        }
        if(keepSearching){
            //Used to move the mouse around a little bit
            WidgetItem(ctx.widgets.find(WidgetID.BANK_PIN_PANEL_ID, 0),ctx=ctx).hover()
        }
    }
}