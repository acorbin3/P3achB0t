package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Context
import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay

class Magic(val ctx: Context) {
    companion object {
        private const val PARENT = WidgetID.SPELLBOOK_GROUP_ID
        private const val FILTER_BUTTON_ID = 187
        enum class Spells(val widgetID: Int) {
            Home_Teleport(5),
            Wind_Strike(6)
        }
    }



    fun isOpen(): Boolean {
        return ctx.tabs.getOpenTab() == Tabs.Tab_Types.Magic
    }

    suspend fun open(waitForActionToComplete: Boolean = true) {
        ctx.tabs.openTab(Tabs.Tab_Types.Magic)
        //Wait for tab to be open
        if (waitForActionToComplete)
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return ctx.tabs.getOpenTab() == Tabs.Tab_Types.Magic
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
                            ctx.client.getSelectedSpellName()
                    ).toLowerCase() == spell.name.replace("_", " ").toLowerCase()
                    && ctx.client.getIsSpellSelected()
            )
                return
        } catch (e: Exception) {
        }
        if (!isOpen()) open()

        val spellWidget = WidgetItem(ctx.widgets.find(PARENT, spell.widgetID), ctx = ctx)
        if (spellWidget.widget != null) {
            println(spellWidget.getInteractPoint())

            spellWidget.interact("Cast")
        }
    }

}