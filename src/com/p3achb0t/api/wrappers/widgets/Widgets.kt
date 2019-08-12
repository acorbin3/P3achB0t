package com.p3achb0t.api.wrappers.widgets

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.Client
import kotlinx.coroutines.delay

class Widgets {
    companion object {
        fun find(parent: Int, child: Int): Component? {
            var widget: Component? = null
            try {
                widget = Client.client.getInterfaceComponents()[parent][child]

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

        fun find(parent: Int, text: String): Component? {
            try {
                for (child in Client.client.getInterfaceComponents()[parent]) {
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