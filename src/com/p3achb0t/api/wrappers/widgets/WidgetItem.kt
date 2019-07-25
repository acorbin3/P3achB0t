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

    fun containsText(text: String): Boolean {
        if (this.widget == null) {
            return false
        } else {
            if (this.widget?.getText()?.toLowerCase()?.contains(text.toLowerCase())!!)
                return true
            else {
                // Need to look at children
                this.widget?.getChildren()?.iterator()?.forEach {
                    if (it.getText().toLowerCase().contains(text.toLowerCase())) {
                        return true
                    }
                }
                return false
            }
        }
    }

    override suspend fun interact(action: String): Boolean {
        val textContains = this.widget?.getText()?.toLowerCase()?.contains(action.toLowerCase())
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