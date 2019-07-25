package com.p3achb0t.api.wrappers.widgets

import com.p3achb0t.Main
import com.p3achb0t.hook_interfaces.Widget

class Widgets {
    companion object {
        fun find(parent: Int, child: Int): Widget? {
            var widget: Widget? = null
            try {
                widget = Main.clientData.getWidgets()[parent][child]

            } catch (e: Exception) {
                return null
            }
            return widget
        }

        fun isWidgetAvaliable(parent: Int, child: Int): Boolean {
            return find(parent, child) != null
        }
    }
}