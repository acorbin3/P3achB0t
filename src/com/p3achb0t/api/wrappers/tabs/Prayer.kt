package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Context
import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID.Companion.PRAYER_GROUP_ID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay

class Prayer(val ctx: Context) {
    //TODO - Quick prayers
    //TODO - Checking if prayer is on
    // resource: how to get settings: https://github.com/07kit/07kit/blob/023c00545b9d104a9b1bda278dd5fdbf85a891f3/src/main/java/com/kit/api/impl/game/SettingsImpl.java
    // resource: settings indexes: https://github.com/07kit/07kit/blob/023c00545b9d104a9b1bda278dd5fdbf85a891f3/src/main/java/com/kit/api/wrappers/Prayer.java

    companion object {
        private const val PARENT = PRAYER_GROUP_ID

        enum class PrayerKind(val widgetID: Int) {
            THICK_SKIN(5),
            BURST_OF_STRENGTH(6),
            CLARITY_OF_THOUGHT(7),
            SHARP_EYE(23),
            MYSTIC_WILL(24),
            ROCK_SKIN(8),
            SUPERHUMAN_STRENGTH(9),
            IMPROVED_REFLEXES(10),
            RAPID_RESTORE(11),
            RAPID_HEAL(12),
            PROTECT_ITEM(13),
            HAWK_EYE(25),
            MYSTIC_LORE(26),
            STEEL_SKIN(14),
            ULTIMATE_STRENGTH(15),
            INCREDIBLE_REFLEXES(16),
            PROTECT_FROM_MAGIC(17),
            PROTECT_FROM_MISSILES(18),
            PROTECT_FROM_MELEE(19),
            EAGLE_EYE(27),
            MYSTIC_MIGHT(28),
            RETRIBUTION(20),
            REDEMPTION(21),
            SMITE(22),
            PRESERVE(33),
            CHIVALRY(29),
            PIETY(30),
            RIGOUR(31),
            AUGURY(32)
        }
    }

    fun isOpen(): Boolean {
        return ctx.tabs.getOpenTab() == Tabs.Tab_Types.Prayer
    }

    suspend fun open(waitForActionToComplete: Boolean = true) {
        ctx.tabs.openTab(Tabs.Tab_Types.Prayer)
        //Wait for tab to be open
        if (waitForActionToComplete)
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return ctx.tabs.getOpenTab() == Tabs.Tab_Types.Prayer
                }
            })
        if (!isOpen()) open()
    }

    suspend fun activate(kind: PrayerKind) {
        if (!isOpen()) open()
        if (isOpen()) {
            val prayer = WidgetItem(ctx.widgets.find(PARENT, kind.widgetID), ctx = ctx)
            prayer.click()
            delay(100)
        }

    }

    suspend fun isPietyActive(): Boolean {

        return ctx.vars.getVarp(83) >= 67108864
    }

    suspend fun isProtectMeleActive(): Boolean {

        return ctx.players.getLocal().player.getHeadIconPrayer() == 0
    }

    suspend fun isProtectRangeActive(): Boolean {

        return ctx.players.getLocal().player.getHeadIconPrayer() == 1
    }

    suspend fun isProtectMageActive(): Boolean {

        return ctx.players.getLocal().player.getHeadIconPrayer() == 2
    }

    suspend fun disable(kind: PrayerKind) {
        if (!isOpen()) open()

        val prayer = WidgetItem(ctx.widgets.find(PARENT, kind.widgetID), ctx = ctx)
        prayer.click()
        delay(100)
    }
}