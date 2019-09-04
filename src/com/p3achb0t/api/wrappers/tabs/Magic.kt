package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.Client
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import kotlinx.coroutines.delay

class Magic(val client: com.p3achb0t._runestar_interfaces.Client) {
    companion object {
        private const val PARENT = WidgetID.SPELLBOOK_GROUP_ID
        private const val FILTER_BUTTON_ID = 187
        enum class Spells(val widgetID: Int) {
            Wind_Strike(5)
        }
    }



    fun isOpen(): Boolean {
        return Tabs(client).getOpenTab() == Tabs.Tab_Types.Magic
    }

    suspend fun open(waitForActionToComplete: Boolean = true) {
        Tabs(client).openTab(Tabs.Tab_Types.Magic)
        //Wait for tab to be open
        if (waitForActionToComplete)
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Tabs(client).getOpenTab() == Tabs.Tab_Types.Magic
                }
            })
        if (!isOpen()) open()
    }

    //TODO - Select spell
    //TODO - deselect spell


    suspend fun cast(spell: Spells) {
        // Ingore if spell is already selected
        try {
            if (Utils.cleanColorText(
                            client.getSelectedSpellName()
                    ).toLowerCase() == spell.name.replace("_", " ").toLowerCase()
                    && client.getIsSpellSelected()
            )
                return
        } catch (e: Exception) {
        }
        if (!isOpen()) open()

        val spellWidget = WidgetItem(Widgets.find(client, PARENT, spell.widgetID), client =client)
        if (spellWidget.widget != null) {
            println(spellWidget.getInteractPoint())

            spellWidget.interact("Cast")
        }
    }

}