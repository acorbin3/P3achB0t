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

        fun find(parent: Int, text: String): Widget? {
            try {
                for (child in Main.clientData.getWidgets()[parent]) {
                    if (child != null) {
                        val tempWidget = WidgetItem(child)
                        if (tempWidget.containsText(text)) {
                            return child
                        }
                    }
                }

            } catch (e: Exception) {
                e.stackTrace.iterator().forEach {
                    println(it.toString())
                }

                return null
            }
            return null

        }

        fun isWidgetAvaliable(parent: Int, child: Int): Boolean {
            return find(parent, child) != null
        }
    }
}