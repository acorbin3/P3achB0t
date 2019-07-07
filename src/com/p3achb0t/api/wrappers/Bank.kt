package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import com.p3achb0t.hook_interfaces.Widget
import kotlinx.coroutines.delay
import java.awt.Rectangle

class Bank {
    //TODO - open bank
    //TODO - deposit all items
    //TODO - deposit all items from a list
    //TODO - get items from bank from id and count
    //TODO - get
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

    companion object {

        suspend fun open() {

            //First look for bankers, if that doesnt work then look for bank objects
            if (!isOpen()) {
                val bankers = NPC.findNpc("Banker")
                if (bankers.size > 0) {
                    bankers[0].interact("Use")
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
                val itemContainer = Widgets.find(WidgetID.BANK_GROUP_ID, WidgetID.Bank.INVENTORY_ITEM_CONTAINER)
                itemContainer?.getChildren()?.iterator()?.forEach {
                    val actions = it.getActions()
                    if (actions != null) {
                        actions.iterator().forEach { action ->
                            if (action != null) {
                                if (action == "Close") {
                                    println("Closing bank")
                                    WidgetItem(it).click()
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
            val depositAllWidget = WidgetItem(Widgets.find(WidgetID.BANK_GROUP_ID, WidgetID.Bank.DEPOSIT_INVENTORY))
            depositAllWidget.click()
        }

        fun itemVisible(itemRect: Rectangle): Boolean {
            return WidgetItem(getWidget()).area.intersects(itemRect)
        }

        suspend fun withdraw(id: Int, count: Int = 1) {
            if (isOpen()) {
                val items = getAll()
                items.forEach {
                    if (it.id == id) {
                        //Check to see if its bvisible
                        if (itemVisible(it.area)) {
                            val itemCount = getItemCount(id)
                            it.interact("Withdraw-5")
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

        suspend fun deposit(id: Int, count: Int = 1) {
            if (isOpen()) {
                val itemCount = getItemCount(id)
                Inventory.getItem(id)?.interact("Deposit-5")
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

        fun getWidget(): Widget? {
            return Widgets.find(WidgetID.BANK_GROUP_ID, WidgetID.Bank.ITEM_CONTAINER)
        }

        fun getSize(): Int {
            if (isOpen()) {
                val widget = Widgets.find(WidgetID.BANK_GROUP_ID, WidgetID.Bank.SIZE)
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
                            stackSize = it.getItemStackSize(),
                            type = WidgetItem.Type.BANK
                        )
                    )
                }

                itemCount += 1
            }

            return itemWidgets
        }
    }
}