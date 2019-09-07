package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.tabs.Inventory
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import com.p3achb0t.ui.Context
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
            val bankers = NPCs(ctx).findNpc("Banker")
            if (bankers.size > 0) {
                bankers[0].interact("Use" )
                Utils.waitFor(3, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return isOpen()
                    }
                })
            }
        }
        //TODO - interact with bank booths is player is not here

    }

    suspend fun close() {
        if (isOpen()) {
            // Loop over the children to find the button that can close the bank
            val itemContainer = Widgets.find(ctx, WidgetID.BANK_GROUP_ID, WidgetID.Bank.INVENTORY_ITEM_CONTAINER)
            itemContainer?.getChildren()?.iterator()?.forEach {
                val actions = it.getOps()
                if (actions != null) {
                    actions.iterator().forEach { action ->
                        if (action != null) {
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
        }
    }

    suspend fun depositAll() {
        val depositAllWidget = WidgetItem(Widgets.find(ctx, WidgetID.BANK_GROUP_ID, WidgetID.Bank.DEPOSIT_INVENTORY), ctx = ctx)
        depositAllWidget.click()
        val inventoryCount = Inventory(ctx).getCount()
        Utils.waitFor(3, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return inventoryCount != Inventory(ctx).getCount()
            }
        })
    }

    fun itemVisible(itemRect: Rectangle): Boolean {
        return WidgetItem(getWidget(), ctx = ctx).area.intersects(itemRect)
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
                                            Widgets.find(ctx, WidgetID.CHATBOX_GROUP_ID, WidgetID.Chatbox.FULL_INPUT)
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
                Inventory(ctx).getItem(id)?.interact("Deposit-$count" )
            } else {
                Inventory(ctx).getItem(id)?.interact("Deposit-X")
                //Wait till the * shows up in the chat box
                Utils.waitFor(3, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        val chatText = Widgets.find(ctx, WidgetID.CHATBOX_GROUP_ID, WidgetID.Chatbox.FULL_INPUT)
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
        return getWidget() != null
    }

    fun getWidget(): Component? {
        return Widgets.find(ctx, WidgetID.BANK_GROUP_ID, WidgetID.Bank.ITEM_CONTAINER)
    }

    fun getSize(): Int {
        if (isOpen()) {
            val widget = Widgets.find(ctx, WidgetID.BANK_GROUP_ID, WidgetID.Bank.SIZE)
            if (widget != null && widget.getText() != null && widget.getText().trim().isNotEmpty()) {
                return widget.getText().trim().toInt()
            }
        }
        return 0
    }

    fun getAll(): ArrayList<WidgetItem> {
        val itemWidgets = ArrayList<WidgetItem>()
        val bank = getWidget()
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
}