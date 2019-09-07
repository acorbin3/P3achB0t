package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.ClientMode
import com.p3achb0t.api.wrappers.Interact
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.ui.Context
import kotlinx.coroutines.delay

class Tabs(val ctx: Context) {
    // Section of widgetID IDs for tabs
    enum class Tab_Types(val id: Int, val resizeID: Int = 0) {
        None(0),
        ClanChat(31, 35),
        AccountManagement(32, 36),
        FriendsList(33, 37),
        Logout(34),
        Options(35, 38),
        Emotes(36, 39),
        Music(37, 40),
        Combat(48, 50),
        Skills(49, 51),
        QuestList(50, 52),
        Inventory(51, 53),
        Equiptment(52, 54),
        Prayer(53, 55),
        Magic(54, 56);

        companion object {
            fun valueOf(id: Int, ctx: Context): Tab_Types? = values().find {
                if (ClientMode(ctx).getMode() == ClientMode.Companion.ModeType.FixedMode)
                    it.id == id
                else
                    it.resizeID == id
            }
        }
    }

    companion object {
        const val PARENT_ID = 548
        const val RESIZE_PARENT_ID = 164
        private val TOP_ROW = 48..54
        private val BOTTOM_ROW = 31..37

        private val RESIZE_TOP_ROW = 50..56
        private val RESIZE_BOTTOM_ROW = 35..40
    }


    // This function
    suspend fun minimizeTab() {
        val tab = getOpenTab()
        // Dont need to minimize a tab when already none are open
        if (tab != null && tab == Tab_Types.None) return

        val parentID =
                if (ClientMode(ctx).getMode() == ClientMode.Companion.ModeType.FixedMode) PARENT_ID else RESIZE_PARENT_ID
        val childID =
                if (ClientMode(ctx).getMode() == ClientMode.Companion.ModeType.FixedMode) tab?.id else tab?.resizeID
        if (childID != null) {
            val widget = ctx.client.getInterfaceComponents()[parentID][childID]
            WidgetItem(widget, ctx = ctx).click()
        }
    }

    suspend fun isTabFlashing(tab: Tab_Types): Boolean {

        var noneCount = 0
        var tabOpenCount = 0
        for (i in 1..30) {
            val openTab = getOpenTab()
            if (tab == openTab) tabOpenCount += 1
            if (Tab_Types.None == openTab) noneCount += 1
            delay(25)
        }
        return noneCount > 3 && tabOpenCount > 3
    }

    fun getOpenTab(): Tab_Types? {
        var tab: Tab_Types? =
                Tab_Types.None
        try {
            val top =
                    if (ClientMode(ctx).getMode() == ClientMode.Companion.ModeType.FixedMode) TOP_ROW else RESIZE_TOP_ROW
            val bottom =
                    if (ClientMode(ctx).getMode() == ClientMode.Companion.ModeType.FixedMode) BOTTOM_ROW else RESIZE_BOTTOM_ROW
            val parentID =
                    if (ClientMode(ctx).getMode() == ClientMode.Companion.ModeType.FixedMode) PARENT_ID else RESIZE_PARENT_ID
            for (childID in top) {
                val widget = ctx.client.getInterfaceComponents()[parentID][childID]
                if (widget.getSpriteId2() > 0) {
                    tab = Tab_Types.valueOf(childID,ctx)
                }

            }
            for (childID in bottom) {
                val widget = ctx.client.getInterfaceComponents()[parentID][childID]
                if (widget.getSpriteId2() > 0) {
                    tab = Tab_Types.valueOf(childID, ctx)
                }

            }
        } catch (e: Exception) {
        }
        return tab
    }

    suspend fun openTab(tab: Tab_Types) {
        try {
            if (getOpenTab() == tab) return
            println("Opening Tab: ${tab.name}")
            val parentID =
                    if (ClientMode(ctx).getMode() == ClientMode.Companion.ModeType.FixedMode) PARENT_ID else RESIZE_PARENT_ID
            val childID =
                    if (ClientMode(ctx).getMode() == ClientMode.Companion.ModeType.FixedMode) tab.id else tab.resizeID
            val widget = ctx.client.getInterfaceComponents()[parentID][childID]
            if (!widget.getIsHidden()) {
                val interactRect = Widget.getDrawableRect(widget,ctx)
                Interact(ctx).interact(interactRect)
                Utils.waitFor(2, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return getOpenTab() == tab
                    }
                })
            }
        } catch (e: Exception) {
        }
    }
}