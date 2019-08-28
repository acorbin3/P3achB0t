package com.p3achb0t.api.wrappers.widgets

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Utils
import kotlinx.coroutines.delay

class Widgets {
    companion object {
        fun find(ctx: Client, parent: Int, child: Int): Component? {
            var widget: Component? = null
            try {
                widget = ctx.getInterfaceComponents()[parent][child]

            } catch (e: Exception) {
                return null
            }
            return widget
        }

        suspend fun waitTillWidgetNotNull(ctx: Client,parent: Int, child: Int) {
            Utils.waitFor(2, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return find(ctx, parent, child) != null
                }
            })
        }

        fun find(ctx: Client, parent: Int, text: String): Component? {
            try {
                for (child in ctx.getInterfaceComponents()[parent]) {
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

        fun isWidgetAvaliable(ctx: Client, parent: Int, child: Int): Boolean {
            return find(ctx, parent, child) != null
        }
        fun getWidgetDetails(widget: Component,index: Int): String{
            var result = "--$index--\n"
            try {
                val containerID = widget.getId().shr(16)
                val childID = widget.getId().and(0xFF)
                result += "Widget ID:" + widget.getId() + "($containerID,$childID)\n"
                val parentIndex = Widget.getParentIndex(widget)
                result += "raw parent ID: ${widget.getParentId()}\n"
                result += "parent Index ${parentIndex.parentID}, ${parentIndex.childID} raw:${parentIndex.raw}\n"
                val chainedParentIndexes = Widget.getChainedParentIndex(widget, ArrayList())
                result += "parent Indexes:["
                chainedParentIndexes.forEach {
                    result += "(${it.parentID},${it.childID}),"
                }
                result += "\n"
                result += "Type: ${widget.getType()}"
                result += "Text:" + widget.getText() + "\n"
                var actions = "["
                if (widget.getItemActions() != null) {
                    widget.getItemActions().iterator().forEach { actions += "$it," }
                }
                result += "Actions:$actions]\n"
                result += "Sprite ID:" + widget.getSpriteId() + "\n"
                result += "Sprite2 ID:" + widget.getSpriteId2() + "\n"
                result += "Item ID:" + widget.getItemId() + "\n"
                result += "getChildIndex:" + widget.getChildIndex() + "\n"
                result += "Raw Position: ${widget.getX()},${widget.getY()}\n"
                result += "Position: ${Widget.getWidgetX(widget)},${Widget.getWidgetY(widget)}\n"
                result += "Scroll(x,y) ${widget.getScrollX()},${widget.getScrollY()}\n"
                result += "Scroll Heigth: ${widget.getScrollHeight()}\n"
                result += "Scroll Width: ${widget.getScrollWidth()}\n"
                result += "Height:" + widget.getHeight() + "\n"
                result += "Width:" + widget.getWidth() + "\n"
                result += "StackCount" + widget.getItemQuantity() + "\n"
                result += "SpriteID:" + widget.getSpriteId() + "\n"
                result += " Color: " + widget.getColor() + "\n"
                result += " Color2: " + widget.getColor2() + "\n"
                result += "Action Type: " + widget.getType() + "\n"
                result += "Hidden: " + widget.getIsHidden() + "\n"
                result += "Spell: ${widget.getSpellName()}\n"
                result += "Dynamic Values: ["
                result += "]\n"
                if (widget.getItemIds() != null) {
                    result += "Slot IDs:"
                    widget.getItemIds().iterator().forEach { result += "$it,\t" }
                    result += "]\n"
                }

                if (widget.getItemQuantities() != null) {
                    result += "Slot Stack sizes:"
                    widget.getItemQuantities().iterator().forEach { result += "$it,\t" }
                    result += "]\n"
                }
                result += "---Internal---\n"
                result += "Bounds Index: ${widget.getRootIndex()}\n"
                result += "Bounds x: ["
                for (i in com.p3achb0t.api.wrappers.Client.client.getRootComponentXs()) {
                    result += "$i,"
                }
                result += "]\n"

                result += "Bounds y: ["
                for (i in com.p3achb0t.api.wrappers.Client.client.getRootComponentYs()) {
                    result += "$i,"
                }
                result += "]\n"

                result += "Bounds Width: ["
                for (i in com.p3achb0t.api.wrappers.Client.client.getRootComponentWidths()) {
                    result += "$i,"
                }
                result += "]\n"

                result += "Bounds Height: ["
                for (i in com.p3achb0t.api.wrappers.Client.client.getRootComponentHeights()) {
                    result += "$i,"
                }
                result += "]\n"

                result += "-----\n"
//            result += "Children: ${widget.getChildren().size}"
                var i = 0
                widget.getChildren().iterator().forEach {
                    result += getWidgetDetails(it, i)
                    i += 1
                }
//                if (widget.getChildren().isNotEmpty()) {
//                widget.getChildren().iterator().forEach { result += getWidgetDetails(it) }
//            }
            } catch (e: Exception) {
                return result
            }
            return result
        }
    }
}