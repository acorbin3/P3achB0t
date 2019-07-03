package com.p3achb0t.hook_interfaces

import com.p3achb0t.Main
import java.awt.Rectangle

interface Widget : Node {
	data class WidgetIndex(var parentID: String, var childID: String)
	companion object {

		fun getParentIndex(widget: Widget): WidgetIndex {
			val parentIndex = widget.getParentId().shr(16)
			val childIndex = widget.getParentId().and(0xFF)
			return WidgetIndex(parentIndex.toString(), childIndex.toString())
		}

		private fun getBoundInfo(widget: Widget): Rectangle {
			return if (widget.getBoundsIndex() >= 0) {
				val clazz = Main.client!!::class.java

				val widgetX = Main.clientData.getWidgetBoundsX()[widget.getBoundsIndex()]
				val widgetY = Main.clientData.getWidgetBoundsY()[widget.getBoundsIndex()]
				val widgetHeight = Main.clientData.getWidgetHeights()[widget.getBoundsIndex()]
				val widgetWidth = Main.clientData.getWidgetWidths()[widget.getBoundsIndex()]


				Rectangle(widgetX, widgetY, widgetWidth, widgetHeight)
			} else {
				Rectangle(-1, -1, -1, -1)
			}
		}

		fun getDrawableRect(widget: Widget): Rectangle {
			return Rectangle(getwidgetX(widget), getwidgetY(widget), widget.getWidth(), widget.getHeight())
		}

		fun getWidget(index: WidgetIndex): Widget {
			return Main.clientData.getWidgets()[index.parentID.toInt()][index.childID.toInt()]
		}

		fun getwidgetX(widget: Widget): Int {
			return if (widget.getParentId() > 0) {

				val parentIndex = getParentIndex(widget)
				val parentWidget = getWidget(parentIndex)
				getwidgetX(parentWidget) + widget.getX() - widget.getScrollX()
			} else {
				getBoundInfo(widget).x
			}
		}

		private fun getwidgetY(widget: Widget): Int {
			return if (widget.getParentId() > 0) {
				val parentIndex = getParentIndex(widget)
				val parentWidget = getWidget(parentIndex)
				getwidgetY(parentWidget) + widget.getY() - widget.getScrollY()
			} else {
				getBoundInfo(widget).y
			}
		}

		fun getItemsRects(widget: Widget): MutableList<Rectangle> {
			val items = mutableListOf<Rectangle>()
			val columns = widget.getWidth()
			val rows = widget.getHeight()
			val baseX = getwidgetX(widget) + widget.getX()
			val baseY = getwidgetY(widget) + widget.getY()

			for (i in 0 until (columns * rows)) {
				val row = i / columns
				val col = i % columns
				val _x = baseX + ((32 + 10) * col)
				val _y = baseY + ((32 + 4) * row)
				items.add(Rectangle(_x, _y, 32, 32))
			}
			return items
		}
	}

	fun getActionType(): Int
	fun getActions(): Array<String>
	fun getBorderThickness(): Int
	fun getBoundsIndex(): Int
	fun getChildTextureId(): Int
	fun getChildren(): Array<Widget>
	fun getComponentIndex(): Int
	fun getDisabledMediaId(): Int
	fun getDisabledMediaType(): Int
	fun getDynamicValue(): Array<IntArray>
	fun getEnabledMediaId(): Int
	fun getEnabledMediaType(): Int
	fun getHeight(): Int
	fun getHidden(): Boolean
	fun getWidget_id(): Int
	fun getItemId(): Int
	fun getItemStackSize(): Int
	fun getLoopCycle(): Int
	fun getName(): String
	fun getParent(): Widget
	fun getParentId(): Int
	fun getScrollMax(): Int
	fun getScrollX(): Int
	fun getScrollY(): Int
	fun getSelectedAction(): String
	fun getShadowColor(): Int
	fun getSlotIds(): IntArray
	fun getSlotStackSizes(): IntArray
	fun getSpell(): String
	fun getSpriteId(): Int
	fun getText(): String
	fun getTextColor(): Int
	fun getTextureId(): Int
	fun getTooltip(): String
	fun getType(): Int
	fun getWidth(): Int
	fun getX(): Int
	fun getY(): Int
}
