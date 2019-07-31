package com.p3achb0t.api.wrappers.widgets

import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.hook_interfaces.Widget
import java.awt.Point
import java.awt.Rectangle
import kotlin.random.Random
import kotlin.random.nextInt

class WidgetItem(
    var widget: Widget? = null,
    var area: Rectangle = widget?.let { Widget.getDrawableRect(it) } ?: Rectangle(),
    var id: Int = widget?.getItemId() ?: 0,
    var stackSize: Int = widget?.getItemStackSize() ?: 0,
    var index: Int = 0,
    var type: Type = Type.SHOP
) : Interactable {
    override suspend fun clickOnMiniMap(): Boolean {
        println("Widgets are not a thing to click on minimap")
        return false
    }

    override fun getInteractPoint(): Point {
        val x = area.centerX + Random.nextInt(-(area.width / 4)..(area.width / 4))
        val y = area.centerY + Random.nextInt(-(area.height / 4)..(area.height / 4))
        return Point(x.toInt(), y.toInt())
    }

    enum class Type {
        SHOP, INVENTORY, BANK, EQUIPMENT, NULL
    }

    //    var itemComposite: ItemComposite get() = Main.clientData.getItem
    fun getBasePoint(): Point {
        return Point(area.x, area.y)
    }

    fun containsText(text: String, includeChildren: Boolean = true): Boolean {
        return if (this.widget != null)
            doesWidgetContainText(this.widget!!, text, includeChildren)
        else false
    }

    override suspend fun interact(action: String): Boolean {
        val textContains = (this.widget?.getText()?.toLowerCase()?.contains(action.toLowerCase()) ?: false
                || this.widget?.getSelectedAction()?.toLowerCase()?.contains(action.toLowerCase()) ?: false)
        if (textContains != null && textContains)
            return super.interact(action)
        else {
            // Need to look at children
            this.widget?.getChildren()?.iterator()?.forEach {
                if (it.getText().toLowerCase().contains(action)) {
                    return WidgetItem(it).interact(action)
                }
            }
            return false
        }
    }


}

//This class is used to create a widget explorer to find widgets
// To lanch the widget explorer you just
// 1. Create an app object/class:// class MyApp : App(WidgetExplorer::class)
// 2. Then need to call: //    launch<MyApp>(args)
fun getStrippedWidgetDetails(widget: Widget, includeChildren: Boolean = true): String {
    var result = ""
    try {
        result += widget.getWidget_id().toString() + "\n"
        result += widget.getText() + "\n"
        var actions = ""
        if (widget.getActions() != null) {
            widget.getActions().iterator().forEach { actions += "$it," }
        }
        result += "$actions\n"
        result += widget.getChildTextureId().toString() + "\n"
        result += widget.getItemId().toString() + "\n"
        result += widget.getComponentIndex().toString() + "\n"
        result += widget.getHeight().toString() + "\n"
        result += widget.getWidth().toString() + "\n"
        result += widget.getItemStackSize().toString() + "\n"

        result += widget.getSpriteId().toString() + "\n"
        result += widget.getShadowColor().toString() + "\n"
        result += widget.getActionType().toString() + "\n"
        result += widget.getEnabledMediaId().toString() + "\n"
        result += widget.getEnabledMediaType().toString() + "\n"
        result += widget.getDisabledMediaId().toString() + "\n"
        result += widget.getDisabledMediaType().toString() + "\n"
        result += widget.getHidden().toString() + "\n"
        result += widget.getTextureId().toString() + "\n"
        result += widget.getTooltip() + "\n"
        result += widget.getSelectedAction() + "\n"
        result += widget.getTextColor().toString() + "\n"

        if (includeChildren)
            widget.getChildren().iterator().forEach { result += getStrippedWidgetDetails(it) }
    } catch (e: Exception) {
        return result
    }
    return result
}

fun doesWidgetContainText(widget: Widget, filter: String, includeChildren: Boolean = true): Boolean {
    val text = getStrippedWidgetDetails(widget, includeChildren)
    return text.contains(filter)
}