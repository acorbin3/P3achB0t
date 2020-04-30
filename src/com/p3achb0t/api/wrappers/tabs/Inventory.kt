package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Context
import com.p3achb0t.api.interfaces.Component
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import java.awt.Rectangle
import java.text.DecimalFormat
import kotlin.random.Random

class Inventory(val ctx: Context? = null) {

    companion object {
        private const val PARENT_ID = WidgetID.INVENTORY_GROUP_ID
        private const val CHILD_ID = 0
    }

    //The concept here is to track items that are picked up from the ground. The addItemToTrack will
    // only be called when an item is trying to be picked up
    data class Item(val id: Int, val name: String)

    val isfull: Boolean
        get() {
            return this.getCount() == 28
        }
    val itemsToTrack = ArrayList<Item>()
    val totalTrackedItemCount = HashMap<Int, Int>() // Key is an item ID, value is the item picked up count
    val curTrackedItemCount = HashMap<Int, Int>() // Key is an item ID, value is the current item count in inventory
    fun addItemToTrack(id: Int) {
        if (itemsToTrack.none { it.id == id }) {
            itemsToTrack.add(Item(id, ctx?.cache?.getItemName(id) ?: "$id"))
            totalTrackedItemCount[id] = 0
            val curCount = getCount(id)
            val stackedCount = getCount(id, true)
            curTrackedItemCount[id] = if (stackedCount > 1) stackedCount else curCount
        }
    }

    fun getSelectedItemID(): Int? {
        if(ctx?.client?.getIsItemSelected() == 0) return 0 else return ctx?.client?.getSelectedItemId()
    }

    fun updateTrackedItems() {
        itemsToTrack.forEach {
            val curCount = getCount(it.id)
            val stackedCount = getCount(it.id, true)
            var diff: Int = 0
            if (stackedCount > 1) {
                diff = stackedCount - curTrackedItemCount[it.id]!!
                curTrackedItemCount[it.id] = stackedCount
            } else {
                diff = curCount - curTrackedItemCount[it.id]!!
                curTrackedItemCount[it.id] = curCount
            }
            //We could have negative if the player is banking, just ignore that
            if (diff > 0) {
                totalTrackedItemCount[it.id] = totalTrackedItemCount[it.id]!! + diff
            }
        }
    }

    fun trackedItemPerHour(id: Int): String {
        val df = DecimalFormat("###,###,###")
        val itemsPerHour = totalTrackedItemCount[id]?.toDouble()!! / ctx?.stats?.runtime?.elapsed!! * 3_600_000
        return df.format(itemsPerHour)
    }


    suspend fun open() {

        if (!isOpen()) Tabs(ctx!!).openTab(Tabs.Tab_Types.Inventory)
    }

    fun isOpen(): Boolean {
        return Tabs(ctx!!).getOpenTab() == Tabs.Tab_Types.Inventory
    }

    suspend fun waitTillInventorySizeChanges(waitTime: Int = 10) {
        val oldInventorySize = getCount()
        delay(Random.nextLong(450, 600))
        Utils.waitFor(waitTime, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return oldInventorySize != getCount()
            }
        })
    }

    suspend fun waitTillNotedItemChanges(notedItemID: Int, waitTime: Int = 10) {
        val oldNotedSize = getCount(notedItemID, useStack = true)
        Utils.waitFor(waitTime, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return oldNotedSize != getCount(notedItemID, useStack = true)
            }
        })
    }

    fun getAll(): ArrayList<WidgetItem> {
        try {
            val items = ArrayList<WidgetItem>()
            val inventory = getWidget()
            // Weird hack check to ensure inventory widget has correct x position. On logon I have seen it return zero
            if (inventory != null) {
                val ids = inventory.getItemIds()
                val stacks = inventory.getItemQuantities()
                val columns = inventory.getWidth()
                val rows = inventory.getHeight()
                val baseX = Widget.getWidgetX(inventory, ctx!!)
                val baseY = Widget.getWidgetY(inventory, ctx)
                for (i in 0 until ids.size) {
                    if (ids[i] > 0 && stacks[i] > 0) {
                        val row = i / columns
                        val col = i % columns
                        val _x = baseX + ((32 + 10) * col)
                        val _y = baseY + ((32 + 4) * row)
                        val area = Rectangle(_x, _y, 32, 32)
                        items.add(
                                WidgetItem(
                                        widget = inventory,
                                        id = ids[i] - 1,
                                        index = i,
                                        stackSize = stacks[i],
                                        type = WidgetItem.Type.INVENTORY,
                                        area = area,
                                        ctx = ctx
                                )
                        )
                    }
                }
            }
            return items
        } catch (e: Exception)  {
            println("getall exception")
            return ArrayList()
        }
    }

    fun getAllIds(): ArrayList<Int> {
        val items = ArrayList<Int>()
        val inventory = getWidget()
        // Weird hack check to ensure inventory widget has correct x position. On logon I have seen it return zero
        if (inventory != null && Widget.getDrawableRect(inventory, ctx!!).x > 0) {
            val ids = inventory.getItemIds()
            ids.forEach {
                if (it != 996 && it > 0) {
                    items.add(it - 1)
                }
            }
        }
        return items
    }

    fun getfirstIndex(id: Int): Int {
        var count = 0
        var index = 0
        var founditem = false
        val inventory = getWidget()
        val items = inventory?.getItemIds()
        items?.forEach {
            var ID = 0
            ID = it
            ID = ID - 1
            if (ID == id && !founditem) {
                index = count
                founditem = true
            }
            count++
        }
        return index
    }


    fun hasPrayerPots(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(2434, 139, 141, 143)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasStaminaPots(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(12625, 12627, 12629, 12631)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }


    fun getPrayerDoses(): Int {
        var doses = 0
        val ItemsNeeded: IntArray = intArrayOf(2434, 139, 141, 143)
        ItemsNeeded.forEach {
            if (it == 2434) {
                if (getCount(it) > 0) {
                    doses = doses + (getCount(it) * 4)
                }
            }
            if (it == 143) {
                if (getCount(it) > 0) {
                    doses = doses + (getCount(it) * 3)
                }
            }
            if (it == 141) {
                if (getCount(it) > 0) {
                    doses = doses + (getCount(it) * 2)
                }
            }
            if (it == 139) {
                if (getCount(it) > 0) {
                    doses = doses + getCount(it)
                }
            }

        }
        return doses
    }

    fun hasextendedSuperAntiFire(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(22209, 22212, 22215, 22218)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasextendedAntiFire(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(11951, 11953, 11955, 11957)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasDivineCombats(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(23685, 23688, 23691, 23694)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasPassage(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(21146, 21149, 21151, 21153, 21155)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasGlory(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(1706, 1708, 1710, 1712, 11976,11978)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasGamesNeck(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(3853, 3855, 3857, 3859, 3861, 3863,3865,3867)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasDivineMagics(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(23754, 23751, 23748, 23745)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasAntiVenom(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(12919, 12917, 12915, 12913)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasDivineRange(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(23733, 23736, 23739, 23742)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun containsAny(itemid: ArrayList<WidgetItem>): Boolean {
        var contains = false
        var items = getAll()
        items.forEachIndexed { index, widgetItem ->
            itemid.forEach {
                if (widgetItem.id == it.id && widgetItem.id != 995) {
                    contains = true
                }
            }
        }
        return contains
    }

    fun containsAny(itemid: List<Int>): Boolean {
        var contains = false
        var items = getAll()
        items.forEachIndexed { index, widgetItem ->
            itemid.forEach {
                if (widgetItem.id == it) {
                    contains = true
                }
            }
        }
        return contains
    }

    fun hasDueling(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasPendant(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(11194, 11193, 11192, 11191, 11190)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    fun hasWealth(): Boolean {
        var HasItems = false
        val ItemsNeeded: IntArray = intArrayOf(11980, 11982, 11984, 11986, 11988)
        ItemsNeeded.forEach {
            if (getCount(it) > 0) {
                HasItems = true
            }
        }
        return HasItems
    }

    suspend fun getItem(id: Int): WidgetItem? {
        if (!isOpen()) {
            open()
        }
        if (isOpen()) {

            println("Looking for Item $id")
            val items = getAll()
            items.forEach {
                if (it.id == id) {
                    println("Found Item! $id")
                    return it
                }
            }
        }
        return null
    }

    fun getWidget(): Component? {
        var widget: Component? = null
        try {
            widget = ctx?.client!!.getInterfaceComponents()[149][0]
        } catch (e: Exception) {
            println("get widget exception")
        }
        return widget
    }

    fun isFull(): Boolean {
        return getCount() == 28
    }

    fun isEmpty(): Boolean {
        return getCount() == 0
    }

    fun isEmptyCoins(): Boolean {
        return getCount() == 1
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
        val items = getAll()
        if(!items.isNullOrEmpty()) {
            for (widget in items) {
                if (widget.id == itemID) count += if (useStack) widget.stackSize else 1
            }
        }
        return count
    }

    fun contains(itemID: Int): Boolean {
        return getCount(itemID) > 0
    }

    fun Contains(id: Int): Boolean {
        var Contains = false
        val items = getAll()
        if(!items.isNullOrEmpty()) {
            for (widget in items) {
                if (widget.id == id) Contains = true
            }
        }
        return Contains
    }

    fun ContainsAll(id: ArrayList<Int>): Boolean {
        var Contains = true
        id.forEach {
            if (!Contains(it)) {
                Contains = false
            }
        }
        return Contains
    }

    fun ContainsOnly(id: ArrayList<Int>): Boolean {
        var Contains = false
        if (ContainsAll(id) && getCount() == id.size) {
            Contains = true
        }
        return Contains
    }


    fun getItemCount(itemid: IntArray): Int {
        var count = 0
        if (isOpen()) {
            val items = getAll()
            items.forEachIndexed { index, widgetItem ->
                itemid.forEach {
                    if (widgetItem.id == it) {
                        count = widgetItem.stackSize + count
                        return@forEach
                    }
                }
            }
        }
        return count
    }

}