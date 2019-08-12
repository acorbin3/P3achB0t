package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.MainApplet
import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import kotlinx.coroutines.delay

class Magic {
    companion object {
        private const val PARENT = WidgetID.SPELLBOOK_GROUP_ID
        private const val FILTER_BUTTON_ID = 187

        enum class Spells(val widgetID: Int) {
            Wind_Strike(5)
        }

        fun isOpen(): Boolean {
            return Tabs.getOpenTab() == Tabs.Tab_Types.Magic
        }

        suspend fun open(waitForActionToComplete: Boolean = true) {
            Tabs.openTab(Tabs.Tab_Types.Magic)
            //Wait for tab to be open
            if (waitForActionToComplete)
                Utils.waitFor(2, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Tabs.getOpenTab() == Tabs.Tab_Types.Magic
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
                        MainApplet.clientData.getSelectedSpellName()
                    ).toLowerCase() == spell.name.replace("_", " ").toLowerCase()
                    && MainApplet.clientData.getIsSpellSelected()
                )
                    return
            } catch (e: Exception) {
            }
            if (!isOpen()) open()

            val spellWidget = WidgetItem(Widgets.find(PARENT, spell.widgetID))
            if (spellWidget.widget != null) {
                println(spellWidget.getInteractPoint())

                spellWidget.interact("Cast")
            }
        }

    }
}