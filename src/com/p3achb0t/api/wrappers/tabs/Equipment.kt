package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay

class Equipment(val ctx: Context) {


    //Info from each item comes from child widget in index 1

    companion object {
        const val NODE_ID = 94

        enum class Slot(val widgetID: Int, val cacheIndex: Int = -1, val unequipMenuAction: Int = -1) {
            Head(WidgetID.Equipment.HELMET, 0, 25362446),
            Cape(WidgetID.Equipment.CAPE, 1, 25362447),
            Neck(WidgetID.Equipment.AMULET, 2, 25362448),
            Weapon(WidgetID.Equipment.WEAPON, 3,25362449),
            Body(WidgetID.Equipment.BODY, 4, 25362450),
            Shield(WidgetID.Equipment.SHIELD, 5, 25362451),
            Legs(WidgetID.Equipment.LEGS, 7, 25362452),
            Gloves(WidgetID.Equipment.GLOVES, 9, 25362453),
            Boots(WidgetID.Equipment.BOOTS, 10, 25362454),
            Ring(WidgetID.Equipment.RING, 12, 25362455),
            Quiver(WidgetID.Equipment.AMMO, 13, 25362456),
            EquiptmentStats(17),
            PriceChecker(19),
            ItemsKeptOnDeath(21),
            CallFollower(23),
        }
    }

    fun isOpen(): Boolean {
        return ctx.tabs.getOpenTab() == Tabs.Tab_Types.Equiptment
    }

    fun getEquippeditems():ArrayList<Int>{
        val itemids = ArrayList<Int>()
        Slot.values().forEach {
            if(isEquipmentSlotEquipted(it)){
                println("Item equipped = " + getItemAtSlot(it)!!.id)
                itemids.add(getItemAtSlot(it)!!.id)
            }
        }
        return itemids
    }

    fun Contains(id: Int): Boolean {
        var Contains = false
        Slot.values().forEach {
            if(getItemAtSlot(it)?.id == id ){
                Contains = true
            }
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

    suspend fun open(waitForActionToComplete: Boolean = true) {
        if (isOpen()) return

        println("Opening Equiptment tab")
        ctx.tabs.openTab(Tabs.Tab_Types.Equiptment)
        //Wait for tab to be open
        if (waitForActionToComplete)
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return ctx.tabs.getOpenTab() == Tabs.Tab_Types.Equiptment
                }
            })
        if (!isOpen()) open()
    }

    suspend fun clickButton(slot: Slot, waitForActionToComplete: Boolean = true) {
        val item = getItemAtSlot(slot)
        println("Removing item from ${slot.name} ${item?.area}")
        item?.click()
        // Wait till item gets removed
        if (waitForActionToComplete)
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return !isEquipmentSlotEquipted(slot)
                }
            })
    }

    suspend fun unEquiptItem(slot: Slot, waitForActionToComplete: Boolean = true) {
        val item = getItemAtSlot(slot)
        println("Removing item from ${slot.name} ${item?.area}")
        // Wait till item gets removed
        val doActionParams = DoActionParams(-1, slot.unequipMenuAction, 57, 1, "", "", 0, 0)
        ctx.mouse.overrideDoActionParams = true
        ctx.mouse.doAction(doActionParams)
        delay(600)
        if (waitForActionToComplete)
            Utils.waitFor(1, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return !isEquipmentSlotEquipted(slot)
                }
            })
    }

    suspend fun interactWithSlot(slot: Slot, interaction: String) {
        if(!isOpen()){
            open()
        }
        if(isOpen()) {
            val item = getItemAtSlot(slot)
            println("Interacting with item ${slot.name} ${item?.area}" + " " + interaction)
            item?.interact(interaction)
            // Wait till item gets removed
            delay((250))
        }
    }

    suspend fun duelingcastlewars() {
        val doActionParams = DoActionParams(-1, 25362455, 57, 3, "", "", 0, 0)
        ctx.mouse.overrideDoActionParams = true
        ctx.mouse.doAction(doActionParams)
        delay(600)
    }

    suspend fun duelingclawnwars() {
        val doActionParams = DoActionParams(-1, 25362455, 57, 4, "", "", 0, 0)
        ctx.mouse.overrideDoActionParams = true
        ctx.mouse.doAction(doActionParams)
        delay(600)
    }


    fun isEquipmentSlotEquipted(slot: Slot): Boolean {
        try {
            val item = ctx.items.getItemInfo(
                    NODE_ID,
                    slot.cacheIndex
            )
            if (item.id > -1)
                return true
        } catch (e: Exception) {
            return false
        }
        return false
    }



    fun getItemAtSlot(slot: Slot): WidgetItem? {
        return try {
            val widget = ctx.widgets.find(WidgetID.EQUIPMENT_GROUP_ID, slot.widgetID)
            val item = ctx.items.getItemInfo(
                    NODE_ID,
                    slot.cacheIndex
            )
            WidgetItem(widget, id = item.id, stackSize = item.stackSize, ctx = ctx)
        } catch (e: Exception) {
            null
        }
    }
}