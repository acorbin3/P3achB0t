package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import kotlin.random.Random

class Magic(val ctx: Context) {
    companion object {
        private const val PARENT = WidgetID.SPELLBOOK_GROUP_ID
        private const val FILTER_BUTTON_ID = 187
        enum class Spells(val widgetID: Int, val arg1: Int, val autoCastVarp: Int, val autoCastVal: Int) {
            WIND_STRIKE(6,14286854, 3, 1),
            WATER_STRIKE(9,14286857, 5, 2),
            EARTH_STRIKE(11,14286859, 7, 3),
            FIRE_STRIKE(13,14286861, 9, 4),
            LOW_LEVEL_ALCHEMY(18,14286866, 0, 0),
            CRUMBLE_UNDEAD(27, 14286875, 0, 0),
            HIGH_LEVEL_ALCHEMY(39, 14286887, 0, 0),
            HOME_TELEPORT(5, 14286853,0, 0),
            LEVEL_ONE_ENCHANT(10, 14286858, 0,0 )
        }

    }

    val LOCATION_HOME_TELEPORT = Tile(3236,3220,0,ctx=ctx)

    suspend fun getAutoCastSpell(): Int {
        return ctx.vars.getVarp(108)
    }


    suspend fun castSpell(spell: Spells) {
        if(ctx.tabs.getOpenTab() != Tabs.Tab_Types.Magic){
            ctx.tabs.openTab(Tabs.Tab_Types.Magic)
            delay(Random.nextLong(600,2000))
        }
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
        val spellWidget = WidgetItem(ctx.widgets.find(PARENT, spell.widgetID), ctx = ctx)
        if (spellWidget.widget != null) {
            println(spellWidget.getInteractPoint())

            spellWidget.interact("Cast")
        }
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
}
