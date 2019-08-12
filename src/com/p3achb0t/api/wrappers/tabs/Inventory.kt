package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.wrappers.Client
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.hook_interfaces.Widget
import java.awt.Rectangle

class Inventory {

    companion object {
        private const val PARENT_ID = WidgetID.INVENTORY_GROUP_ID
        private const val CHILD_ID = 0

        suspend fun open() {

            if (!isOpen()) Tabs.openTab(Tabs.Tab_Types.Inventory)
            if (!isOpen()) open()
        }

        fun isOpen(): Boolean {
            return Tabs.getOpenTab() == Tabs.Tab_Types.Inventory
        }

        fun getAll(): ArrayList<WidgetItem> {
            val items = ArrayList<WidgetItem>()
            val inventory = getWidget()
            // Weird hack check to ensure inventory widget has correct x position. On logon I have seen it return zero
            if (inventory != null && Widget.getDrawableRect(inventory).x > 0) {
                val ids = inventory.getItemIds()
                val stacks = inventory.getItemQuantities()

                val columns = inventory.getWidth()
                val rows = inventory.getHeight()
                val baseX = Widget.getWidgetX(inventory)
                val baseY = Widget.getWidgetY(inventory)
                for (i in 0 until (columns * rows)) {
                    if (ids[i] > 0 && stacks[i] > 0) {
                        val row = i / columns
                        val col = i % columns
                        val _x = baseX + ((32 + 10) * col)
                        val _y = baseY + ((32 + 4) * row)
                        val area = Rectangle(_x, _y, 32, 32)
                        items.add(
                            WidgetItem(
                                area = area,
                                id = ids[i] - 1,
                                stackSize = stacks[i],
                                widget = inventory,
                                type = WidgetItem.Type.INVENTORY
                            )
                        )
                    }
                }
            }
            return items
        }

        fun getItem(id: Int): WidgetItem? {
            println("Looking for Item $id")
            val items = getAll()
            items.forEach {
                if (it.id == id) {
                    println("Found Item! $id")
                    return it
                }
            }
            return null
        }

        fun getWidget(): Component? {
            var widget: Component? = null
            try {
                widget = Client.client.getInterfaceComponents()[PARENT_ID][CHILD_ID]
            } catch (e: Exception) {
            }
            return widget
        }

        fun isFull(): Boolean {
            return getCount() == 28
        }

        fun isEmpty(): Boolean {
            return getCount() == 0
        }

        fun getCount(): Int {
            var count = 0
            val widget = getWidget()
            if (widget != null) {
                for (id in widget.getItemIds()) {
                    if (id > 0) count += 1
                }
            }
            return count
        }

        fun getCount(itemID: Int, useStack: Boolean = false): Int {
            var count = 0
            for (widget in getAll()) {
                if (widget.id == itemID) count += if (useStack) widget.stackSize else 1
            }
            return count
        }
    }

}