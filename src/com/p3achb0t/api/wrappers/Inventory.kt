package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.hook_interfaces.Widget
import java.awt.Rectangle

class Inventory {

    companion object {
        private const val PARENT_ID = WidgetID.INVENTORY_GROUP_ID
        private const val CHILD_ID = 0

        suspend fun open() {
            Tabs.openTab(Tabs.Tab_Types.Inventory)
        }

        fun isOpen(): Boolean {
            return Tabs.getOpenTab() == Tabs.Tab_Types.Inventory
        }

        fun getAll(): ArrayList<WidgetItem> {
            val items = ArrayList<WidgetItem>()
            val inventory = getWidget()
            // Weird hack check to ensure inventory widget has correct x position. On logon I have seen it return zero
            if (inventory != null && Widget.getDrawableRect(inventory).x > 0) {
                val ids = inventory.getSlotIds()
                val stacks = inventory.getSlotStackSizes()
                for (i in 0 until ids.size) {
                    if (ids[i] > 0 && stacks[i] > 0) {
                        val rec = Widget.getDrawableRect(inventory)
                        val area =
                            Rectangle(
                                inventory.getX() + ((i % 4) * 42) + rec.x,
                                (inventory.getY() + ((i / 4) * 36) + rec.y),
                                31,
                                31
                            )
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
            val items = getAll()
            items.forEach {
                if (it.id == id)
                    return it
            }
            return null
        }
        fun getWidget(): Widget? {
            var widget: Widget? = null
            try {
                widget = Main.clientData.getWidgets()[PARENT_ID][CHILD_ID]
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
                for (id in widget.getSlotIds()) {
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