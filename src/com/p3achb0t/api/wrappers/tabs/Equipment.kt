package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.Items
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import kotlinx.coroutines.delay

class Equipment(val client: com.p3achb0t._runestar_interfaces.Client) {


    //Info from each item comes from child widget in index 1

    companion object {
        const val NODE_ID = 94

        enum class Slot(val widgetID: Int, val cacheIndex: Int = -1) {
            Head(WidgetID.Equipment.HELMET, 0),
            Cape(WidgetID.Equipment.CAPE, 1),
            Neck(WidgetID.Equipment.AMULET, 2),
            Weapon(WidgetID.Equipment.WEAPON, 3),
            Body(WidgetID.Equipment.BODY, 4),
            Shield(WidgetID.Equipment.SHIELD, 5),
            Legs(WidgetID.Equipment.LEGS, 7),
            Gloves(WidgetID.Equipment.GLOVES, 9),
            Boots(WidgetID.Equipment.BOOTS, 10),
            Ring(WidgetID.Equipment.RING, 12),
            Quiver(WidgetID.Equipment.AMMO, 13),
            EquiptmentStats(17),
            PriceChecker(19),
            ItemsKeptOnDeath(21),
            CallFollower(23),
        }
    }

    fun isOpen(): Boolean {
        return Tabs(client).getOpenTab() == Tabs.Tab_Types.Equiptment
    }

    suspend fun open(waitForActionToComplete: Boolean = true) {
        if (isOpen()) return

        println("Opening Equiptment tab")
        Tabs(client).openTab(Tabs.Tab_Types.Equiptment)
        //Wait for tab to be open
        if (waitForActionToComplete)
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Tabs(client).getOpenTab() == Tabs.Tab_Types.Equiptment
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
        item?.interact("Remove" )
        // Wait till item gets removed
        if (waitForActionToComplete)
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return !isEquipmentSlotEquipted(slot)
                }
            })

    }

    fun isEquipmentSlotEquipted(slot: Slot): Boolean {
        try {
            val item = Items(client).getItemInfo(
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
            val widget = Widgets.find(client, WidgetID.EQUIPMENT_GROUP_ID, slot.widgetID)
            val item = Items(client).getItemInfo(
                    NODE_ID,
                    slot.cacheIndex
            )
            WidgetItem(widget, id = item.id, stackSize = item.stackSize, client = client)
        } catch (e: Exception) {
            null
        }
    }
}