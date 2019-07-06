package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.hook_interfaces.Widget

class Tabs {
    // Section of id IDs for tabs
    enum class Tab_Types(val id: Int) {
        ClanChat(31),
        AccountManagement(32),
        FriendsList(33),
        Logout(34),
        Options(35),
        Emotes(36),
        Music(37),
        Combat(48),
        Skills(49),
        QuestList(50),
        Inventory(51),
        Equiptment(52),
        Prayer(53),
        Magic(54);

        companion object {
            fun valueOf(id: Int): Tab_Types? = values().find { it.id == id }
        }
    }

    companion object {
        const val PARENT_ID = 548


        private val TOP_ROW = 48..54
        private val BOTTOM_ROW = 31..37

        fun getOpenTab(): Tab_Types? {
            var tab: Tab_Types? = Tab_Types.Logout
            try {
                for (childID in TOP_ROW) {
                    val widget = Main.clientData.getWidgets()[PARENT_ID][childID]
                    if (widget.getTextureId() > 0) {
                        tab = Tab_Types.valueOf(childID)
                    }

                }
                for (childID in BOTTOM_ROW) {
                    val widget = Main.clientData.getWidgets()[PARENT_ID][childID]
                    if (widget.getTextureId() > 0) {
                        tab = Tab_Types.valueOf(childID)
                    }

                }
            } catch (e: Exception) {
            }
            return tab
        }

        suspend fun openTab(tab: Tab_Types) {
            val widget = Main.clientData.getWidgets()[PARENT_ID][tab.id]
            val interactRect = Widget.getDrawableRect(widget)
            Interact.interact(interactRect, "")
        }
    }
}