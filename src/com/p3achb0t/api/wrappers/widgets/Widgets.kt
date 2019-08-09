package com.p3achb0t.api.wrappers.widgets

import com.p3achb0t.MainApplet
import com.p3achb0t.api.Utils
import com.p3achb0t.hook_interfaces.Widget
import kotlinx.coroutines.delay

class Widgets {
    companion object {
        fun find(parent: Int, child: Int): Widget? {
            var widget: Widget? = null
            try {
                widget = MainApplet.clientData.getWidgets()[parent][child]

            } catch (e: Exception) {
                return null
            }
            return widget
        }

        suspend fun waitTillWidgetNotNull(parent: Int, child: Int) {
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return find(parent, child) != null
                }
            })
        }

        fun find(parent: Int, text: String): Widget? {
            try {
                for (child in MainApplet.clientData.getWidgets()[parent]) {
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