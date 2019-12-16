package com.p3achb0t.scripts

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.DebugScript
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import java.awt.Graphics

class WidgetExplorerDebug : DebugScript() {
    companion object{
        val scriptName = "widgetExplorer"
    }
    init {
        println("Adding the WidgetExplorer debug script")
        scriptName = WidgetExplorerDebug.scriptName
    }
    var selectedWidget: Component? = null
    var selectedWidgetItem: WidgetItem? = null

    override fun draw(g: Graphics) {
        if (selectedWidget?.getId() != ctx.selectedWidget?.getId()) {
            println("Widget Switched to ${ctx.selectedWidget?.getId()}")
            selectedWidget = ctx.selectedWidget
            selectedWidgetItem = WidgetItem(selectedWidget, ctx = ctx)
        }
        if (selectedWidgetItem != null) {
            selectedWidgetItem?.area?.let { g.drawRect(it.x, it.y, it.width, it.height) }
        }
    }
}