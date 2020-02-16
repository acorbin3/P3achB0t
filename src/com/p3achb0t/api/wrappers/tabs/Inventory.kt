package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import net.runelite.api.MenuOpcode
import java.awt.Rectangle
import kotlin.random.Random

class Inventory(val ctx: Context? = null) {

    companion object {
        private const val PARENT_ID = WidgetID.INVENTORY_GROUP_ID
        private const val CHILD_ID = 0
    }

    suspend fun open() {

        if (!isOpen()) Tabs(ctx!!).openTab(Tabs.Tab_Types.Inventory)
    }

    fun isOpen(): Boolean {
        return Tabs(ctx!!).getOpenTab() == Tabs.Tab_Types.Inventory
    }

    suspend fun waitTillInventorySizeChanges(waitTime: Int = 10){
        val oldInventorySize = getCount()
        delay(Random.nextLong(450,600))
        Utils.waitFor(waitTime, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return oldInventorySize != getCount()
            }
        })
    }
    suspend fun waitTillNotedItemChanges(notedItemID: Int ,waitTime: Int = 10){
        val oldNotedSize = getCount(notedItemID,useStack = true)
        Utils.waitFor(waitTime, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return oldNotedSize != getCount(notedItemID,useStack = true)
            }
        })
    }

    fun getAll(): ArrayList<WidgetItem> {
        val items = ArrayList<WidgetItem>()
        val inventory = getWidget()
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
        val inventory = getWidget()
        val items = inventory?.getItemIds()
        items?.forEach {
            var ID = 0
            ID = it
            ID = ID - 1
            if(ID == id && !founditem){
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

    suspend fun Teleport(id: Int) {
        var items = getAll()
        var index = getfirstIndex(id)
        out_loop@ for (it in items) {
            if (it.id == id) {
                val doActionParams = DoActionParams(index, 9764864, 33, id, "", "", 0, 0)
                ctx?.mouse?.overrideDoActionParams = true
                ctx?.mouse?.doAction(doActionParams)
                delay(600)
                break@out_loop
            }
        }
    }

    suspend fun wear(id: Int) {
        var items = getAll()
        var index = getfirstIndex(id)
        out_loop@ for (it in items) {
            if (it.id == id) {
                val doActionParams = DoActionParams(index, 9764864, 34, id, "", "", 0, 0)
                ctx?.mouse?.overrideDoActionParams = true
                ctx?.mouse?.doAction(doActionParams)
                delay(600)
                break@out_loop
            }
        }
    }

    suspend fun wearInBank(id: Int) {
        var items = getAll()
        var index = getfirstIndex(id)
        out_loop@ for (it in items) {
            if (it.id == id) {
                val doActionParams = DoActionParams(index, 983043, 1007, 9, "", "", 0, 0)
                ctx?.mouse?.overrideDoActionParams = true
                ctx?.mouse?.doAction(doActionParams)
                delay(600)
                break@out_loop
            }
        }
    }

    suspend fun eat(id: Int) {
            var items = getAll()
        var index = getfirstIndex(id)
            out_loop@ for (it in items) {
                if (it.id == id) {
                    val doActionParams = DoActionParams(index, 9764864, 33, id, "", "", 0, 0)
                    ctx?.mouse?.overrideDoActionParams = true
                    ctx?.mouse?.doAction(doActionParams)
                    delay(600)
                    break@out_loop
                }
            }
    }



    suspend fun drink(id: Int) {
        var items = getAll()
        var index = getfirstIndex(id)
        out_loop@ for (it in items) {
            if (it.id == id) {
                val doActionParams = DoActionParams(index, 9764864, 33, id, "", "", 0, 0)
                ctx?.mouse?.overrideDoActionParams = true
                ctx?.mouse?.doAction(doActionParams)
                delay(600)
                break@out_loop
            }
        }
    }

    suspend fun rub(id: Int) {
        var items = getAll()
        var index = getfirstIndex(id)
        out_loop@ for (it in items) {
            if (it.id == id) {
                val doActionParams = DoActionParams(index, 9764864, 36, id, "", "", 0, 0)
                ctx?.mouse?.overrideDoActionParams = true
                ctx?.mouse?.doAction(doActionParams)
                delay(600)
                break@out_loop
            }
        }
    }

    suspend fun rub2(id: Int) {
        var items = getAll()
        var index = getfirstIndex(id)
        out_loop@ for (it in items) {
            if (it.id == id) {
                val doActionParams = DoActionParams(index, 9764864, 35, id, "", "", 0, 0)
                ctx?.mouse?.overrideDoActionParams = true
                ctx?.mouse?.doAction(doActionParams)
                delay(600)
                break@out_loop
            }
        }
    }


    fun getCount(itemID: Int, useStack: Boolean = false): Int {
        var count = 0
        for (widget in getAll()) {
            if (widget.id == itemID) count += if (useStack) widget.stackSize else 1
        }
        return count
    }
    fun contains(itemID: Int): Boolean{
        println("Item $itemID has ${getCount(itemID)} in inventory")
        return getCount(itemID) > 0
    }

    /**
     * added by sirscript
     */

    fun Contains(id: Int): Boolean {
        var Contains = false
        for (widget in getAll()) {
            if (widget.id == id) Contains = true
        }
        return Contains
    }

    fun ContainsAll(id: ArrayList<Int>): Boolean {
        var Contains = true
            id.forEach {
                if(!Contains(it)){
                    println("Can't find " + it + " in inv")
                    Contains = false
                }
        }
        return Contains
    }


    fun getItemCount(itemid: ArrayList<Int>): Int {
        var count = 0
        if (isOpen()) {
            val items = getAll()
            items.forEachIndexed { index, widgetItem ->
                itemid.forEach {
                    if (widgetItem.id == it) {
                        count = widgetItem.stackSize
                        return@forEach
                    }
                }
            }
        }
        return count
    }

}