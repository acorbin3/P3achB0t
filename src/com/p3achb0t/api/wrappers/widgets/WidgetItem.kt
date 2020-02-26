package com.p3achb0t.api.wrappers.widgets

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Context
import com.p3achb0t.api.MenuOpcode
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.interfaces.Interactable
import java.awt.Point
import java.awt.Rectangle
import kotlin.random.Random
import kotlin.random.nextInt

class WidgetItem(
        var widget: Component? = null,
        var area: Rectangle = widget?.let { ctx?.let { it1 -> Widget.getDrawableRect(it, it1) } }
                ?: Rectangle(),
        var id: Int = widget?.getItemId() ?: 0,
        var stackSize: Int = widget?.getItemQuantity() ?: 0,
        var index: Int = 0,
        var type: Type = Type.SHOP,
        ctx: Context? = null
) : Interactable(ctx) {
    override suspend fun clickOnMiniMap(): Boolean {
        println("Widgets are not a thing to click on minimap")
        return false
    }

    override fun getInteractPoint(): Point {
        val x = area.centerX + Random.nextInt(-(area.width / 4)..(area.width / 4))
        val y = area.centerY + Random.nextInt(-(area.height / 4)..(area.height / 4))
        return Point(x.toInt(), y.toInt())
    }

    override fun isMouseOverObj(): Boolean {
        val mousePoint = Point(ctx?.mouse?.getX() ?: -1, ctx?.mouse?.getY() ?: -1)
        return area.contains(mousePoint)
    }

    enum class Type {
        SHOP, INVENTORY, BANK, EQUIPMENT, NULL
    }

    //    var itemComposite: ItemComposite get() = MainApplet.clientData.getItem
    fun getBasePoint(): Point {
        return Point(area.x, area.y)
    }

    fun containsText(text: String, includeChildren: Boolean = true): Boolean {
        return if (this.widget != null)
            doesWidgetContainText(this.widget!!, text, includeChildren)
        else false
    }

    suspend fun doAction(){
        val doActionParams = this.widget?.getId()?.let { DoActionParams(-1, it, MenuOpcode.WIDGET_DEFAULT.id, 1, "", "", 0 ,0) }
        doActionParams?.let { ctx?.mouse?.doAction(it) }
    }

    suspend fun cancel(){
        val doActionParams = this.widget?.getId()?.let { DoActionParams(0, 0, MenuOpcode.CANCEL.id, 1, "", "", 0 ,0) }
        doActionParams?.let { ctx?.mouse?.doAction(it) }
    }
    //

    suspend fun selectSpell(){
        val doActionParams = this.widget?.getId()?.let { DoActionParams(-1, it, MenuOpcode.WIDGET_TYPE_2.id, 1, "", "", 0 ,0) }
        doActionParams?.let { ctx?.mouse?.doAction(it) }
    }


    override suspend fun interact(action: String): Boolean {
        val textContains = (this.widget?.getText()?.toLowerCase()?.contains(action.toLowerCase()) ?: false
                || this.widget?.getTargetVerb()?.toLowerCase()?.contains(action.toLowerCase()) ?: false)
        if (textContains != null && textContains)
            return super.interact(action)
        else {
            // Need to look at children
            this.widget?.getChildren()?.iterator()?.forEach {
                if (it.getText().toLowerCase().contains(action)) {
                    return WidgetItem(it, ctx = ctx).interact(action)
                }
            }
            return super.interact(action)
        }
    }


}

fun getStrippedWidgetDetails(widget: Component, includeChildren: Boolean = true): String {
    var result = ""
    try {
        result += widget.getId().toString() + "\n"
        result += widget.getText() + "\n"
        var actions = ""
        if (widget.getOps() != null) {
            widget.getOps().iterator().forEach { actions += "$it," }
        }
        result += "$actions\n"
        result += widget.getSpriteId().toString() + "\n"
        result += widget.getItemId().toString() + "\n"
        result += widget.getChildIndex().toString() + "\n"
        result += widget.getHeight().toString() + "\n"
        result += widget.getWidth().toString() + "\n"
        result += widget.getItemQuantity().toString() + "\n"

        result += widget.getSpriteId().toString() + "\n"
        result += widget.getSpriteShadow().toString() + "\n"
        result += widget.getButtonType().toString() + "\n"
        result += widget.getModelId2().toString() + "\n"
        result += widget.getModelType2().toString() + "\n"
        result += widget.getModelId().toString() + "\n"
        result += widget.getModelType().toString() + "\n"
        result += widget.getIsHidden().toString() + "\n"
        result += widget.getSpriteId2().toString() + "\n"
        result += widget.getButtonText() + "\n"
        result += widget.getTargetVerb() + "\n"
        result += widget.getColor().toString() + "\n"

        if (includeChildren)
            widget.getChildren().iterator().forEach { result += getStrippedWidgetDetails(it) }
    } catch (e: Exception) {
        return result
    }
    return result
}

fun doesWidgetContainText(widget: Component, filter: String, includeChildren: Boolean = true): Boolean {
    val text = getStrippedWidgetDetails(widget, includeChildren)
    return text.contains(filter)
}