package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import kotlin.random.Random

class Magic(val ctx: Context) {
    companion object {
        private const val PARENT = WidgetID.SPELLBOOK_GROUP_ID
        private const val FILTER_BUTTON_ID = 187
        enum class Spells(val widgetID: Int, val arg1: Int) {
            WIND_STRIKE(6,14286854),
            CRUMBLE_UNDEAD(27, 14286875),
            HIGH_LEVEL_ALCHEMY(39, 14286887)
        }
    }




    suspend fun castSpell(spell: Spells) {
        try {
            if (Utils.cleanColorText(
                            ctx.client.getSelectedSpellName()
                    ).toLowerCase() == spell.name.replace("_", " ").toLowerCase()
                    && ctx.client.getIsSpellSelected()
            )
                return
        } catch (e: Exception) {
        }
        if(ctx.client.getIsSpellSelected()){
            println(Utils.cleanColorText(
                    ctx.client.getSelectedSpellName()
            ).toLowerCase())
        }
        println(spell.name.replace("_", " ").toLowerCase())
        var doActionParams = DoActionParams(-1, spell.arg1,25, 0, "", "", 0, 0)
        ctx?.mouse?.overrideDoActionParams = true
        ctx?.mouse?.doAction(doActionParams)
        delay(100)
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

    suspend fun castSpellonNPC(spell: Spells, menuid: Int) {
        try {
            if (Utils.cleanColorText(
                            ctx.client.getSelectedSpellName()
                    ).toLowerCase() == spell.name.replace("_", " ").toLowerCase()
                    && ctx.client.getIsSpellSelected()
            ) {
                var doActionParams = DoActionParams(0, 0, 8, menuid, "", "", 0, 0)
                ctx?.mouse?.overrideDoActionParams = true
                ctx?.mouse?.doAction(doActionParams)
                delay(100)
            }
        } catch (e: Exception) {
        }

    }

    suspend fun castSpellonInvItem(spell: Spells, id: Int) {
        try {
            if (Utils.cleanColorText(
                            ctx.client.getSelectedSpellName()
                    ).toLowerCase() == spell.name.replace("_", " ").toLowerCase()
                    && ctx.client.getIsSpellSelected()
            ) {
                println("True")
                var doActionParams = DoActionParams(ctx.inventory.getfirstIndex(id), 9764864, 32, id, "", "", 0, 0)
                ctx?.mouse?.overrideDoActionParams = true
                ctx?.mouse?.doAction(doActionParams)
                delay(100)
            }
        } catch (e: Exception) {
        }

    }


}