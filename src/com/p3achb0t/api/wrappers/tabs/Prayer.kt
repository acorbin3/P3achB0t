package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.Utils
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

        //if the varpNumb is -1 means we are missing the varpbit
        enum class PrayerKind(val widgetID: Int,val varpNumb: Int) {
            THICK_SKIN(5,4101),
            BURST_OF_STRENGTH(6,4105),
            CLARITY_OF_THOUGHT(7,4106),
            SHARP_EYE(23,4122),
            MYSTIC_WILL(24,4123),
            ROCK_SKIN(8,4107),
            SUPERHUMAN_STRENGTH(9,4108),
            IMPROVED_REFLEXES(10,4109),
            RAPID_RESTORE(11,4110),
            RAPID_HEAL(12,4111),
            PROTECT_ITEM(13,4112),
            HAWK_EYE(25,4124),
            MYSTIC_LORE(26,4125),
            STEEL_SKIN(14,4113),
            ULTIMATE_STRENGTH(15,4114 ),
            INCREDIBLE_REFLEXES(16,4115),
            PROTECT_FROM_MAGIC(17,4116),
            PROTECT_FROM_MISSILES(18,4117),
            PROTECT_FROM_MELEE(19,4118),
            EAGLE_EYE(27,4126),
            MYSTIC_MIGHT(28,4127),
            RETRIBUTION(20,4119),
            REDEMPTION(21,4120),
            SMITE(22,4121),
            PRESERVE(33,-1),
            CHIVALRY(29,-1),
            PIETY(30,-1),
            RIGOUR(31,-1),
            AUGURY(32,-1)
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

    fun isPrayerActive(prayerKind: PrayerKind): Boolean{
//        println("${prayerKind.name} varbit: ${ctx.vars.getVarbit(prayerKind.varpNumb)}")
        return ctx.vars.getVarbit(prayerKind.varpNumb) == 1
    }

    fun isPietyActive(): Boolean {

        return ctx.vars.getVarp(83) >= 67108864
    }

    fun isMysticMight(): Boolean{
        return ctx.vars.getVarbit(4127) == 1
    }

    fun isProtectMeleActive(): Boolean {

        return ctx.vars.getVarbit(4118) == 1
    }

    fun isProtectRangeActive(): Boolean {

        return ctx.vars.getVarbit(4117) == 1
    }

    fun isEagleEyeActive(): Boolean {

        return ctx.vars.getVarbit(4126) == 1
    }

    fun isProtectMageActive(): Boolean {

        return ctx.vars.getVarbit(4116) == 1
    }

    fun isQuickPrayerActive(): Boolean {

        return ctx.vars.getVarp(375) == 1
    }

    fun getActiveProtectPrayer():String {
        var prayer = "Dummy"
        if(ctx.vars.getVarbit(4118) == 1) prayer = "PROTECT_FROM_MELEE"
        if(ctx.vars.getVarbit(4117) == 1) prayer = "PROTECT_FROM_MISSILES"
        if(ctx.vars.getVarbit(4116) == 1) prayer = "PROTECT_FROM_MAGIC"
        return prayer
    }

    fun isUltimateStrengthActive(): Boolean{
        return  ctx.vars.getVarp(4114) == 1
    }

    fun isUltimateAttackActive(): Boolean{
        return  ctx.vars.getVarp(4115) == 1
    }

    suspend fun disable(kind: PrayerKind) {
        if (!isOpen()) open()

        val prayer = WidgetItem(ctx.widgets.find(PARENT, kind.widgetID), ctx = ctx)
        prayer.click()
        delay(100)
    }

}