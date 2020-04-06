package com.p3achb0t.scripts.paint.widgetexplorer

import com.p3achb0t.api.script.PaintScript
import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.interfaces.Component
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Debug","Widget Explorer","P3aches", "0.1")
class WidgetExplorerPaint : PaintScript() {
    companion object{
        val scriptName = "widgetExplorer"
    }
    init {
        println("Adding the WidgetExplorer debug script")
        //scriptName = Companion.scriptName
    }
    var selectedWidget: Component? = null
    var selectedWidgetItem: WidgetItem? = null

    override fun draw(g: Graphics) {
        g.color = Color.YELLOW
        if (selectedWidget?.getId() != ctx.selectedWidget?.getId() || (selectedWidget?.getChildIndex() != ctx.selectedWidget?.getChildIndex())) {
            println("Widget Switched to ${ctx.selectedWidget?.getId()}")
            selectedWidget = ctx.selectedWidget
            selectedWidgetItem = WidgetItem(selectedWidget, ctx = ctx)
        }
        if (selectedWidgetItem != null) {
            selectedWidgetItem?.area?.let { g.drawRect(it.x, it.y, it.width, it.height) }
        }
    }

    override fun start() {
        WidgetExplorerV3.createWidgetExplorer(ctx)
    }

    override fun stop() {
    }
}