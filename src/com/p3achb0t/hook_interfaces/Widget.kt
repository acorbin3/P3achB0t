package com.p3achb0t.hook_interfaces

import com.p3achb0t.Main
import java.awt.Rectangle

interface Widget : Node {
	data class WidgetIndex(var parentID: String, var childID: String, var raw: Int = 0)
	companion object {

		fun getParentIndex(widget: Widget): WidgetIndex {
			val parentIndex = widget.getParentId().shr(16)
			val childIndex = widget.getParentId().and(0xFFFF)
			if (parentIndex == -1) {
				val containerIndex = widget.getWidget_id().shr(16)
				val hTable = Main.clientData.getWidgetNodes()
				for (node in hTable.getBuckets()) {
					if (node is WidgetNode && node.getWidgetNode_id() == containerIndex) {
						val parent = node.getId().toInt().shr(16)
						val child = node.getId().toInt().and(0xFFFF)
						return WidgetIndex(parent.toString(), child.toString(), node.getId().toInt())

					}
					var next = node.getNext()
					while (next != null && next != node) {
						if (next is WidgetNode && next.getWidgetNode_id() == containerIndex) {
							val parent = next.getId().toInt().shr(16)
							val child = next.getId().toInt().and(0xFFFF)
							return WidgetIndex(parent.toString(), child.toString(), next.getId().toInt())
						}
						next = next.getNext()
					}
				}
			}
			return WidgetIndex(parentIndex.toString(), childIndex.toString())
		}

		fun getChainedParentIndex(widget: Widget, parentList: ArrayList<WidgetIndex>): ArrayList<WidgetIndex> {
			val parentIndex = getParentIndex(widget)
			return if (parentIndex.parentID.toInt() != -1) {
				parentList.add(parentIndex)
				val parent = getWidget(parentIndex)
				getChainedParentIndex(parent, parentList)
			} else {
				parentList
			}
		}

		private fun getBoundInfo(widget: Widget): Rectangle {
			return if (widget.getBoundsIndex() >= 0) {
				val widgetX = Main.clientData.getWidgetBoundsX()[widget.getBoundsIndex()]
				val widgetY = Main.clientData.getWidgetBoundsY()[widget.getBoundsIndex()]
				val widgetHeight = Main.clientData.getWidgetHeights()[widget.getBoundsIndex()]
				val widgetWidth = Main.clientData.getWidgetWidths()[widget.getBoundsIndex()]
				Rectangle(widgetX, widgetY, widgetWidth, widgetHeight)
			} else {
				Rectangle(0, 0, 0, 0)
			}
		}

		fun getDrawableRect(widget: Widget): Rectangle {
//			val boundsIndex = widget.getBoundsIndex()
			//TODO - whats the deal with bounds heigth and width
//			val boundsWidth = if(boundsIndex > 0 && (getParentIndex(widget).parentID.toInt() == -1)) Main.clientData.getWidgetWidths()[boundsIndex] else widget.getWidth()
//			val boundsHeight = if(boundsIndex > 0 && (getParentIndex(widget).parentID.toInt() == -1)) Main.clientData.getWidgetHeights()[boundsIndex] else widget.getHeight()
			val boundsWidth = widget.getWidth()
			val boundsHeight = widget.getHeight()
			val rect = Rectangle(getWidgetX(widget), getWidgetY(widget), boundsWidth, boundsHeight)
			return rect
		}

		fun getWidget(index: WidgetIndex): Widget {
			return Main.clientData.getWidgets()[index.parentID.toInt()][index.childID.toInt()]
		}


		fun getWidgetX(widget: Widget): Int {
			return if (getParentIndex(widget).parentID.toInt() != -1) {
				val parentIndex = getParentIndex(widget)
				val parentWidget = getWidget(parentIndex)
				val finalX = (getWidgetX(parentWidget) + widget.getX() - parentWidget.getScrollX())
				finalX
			} else {

				getBoundInfo(widget).x
			}
		}

		fun getWidgetY(widget: Widget): Int {

			return if (getParentIndex(widget).parentID.toInt() != -1) {
				val parentIndex = getParentIndex(widget)
				val parentWidget = getWidget(parentIndex)
				val finalX = (getWidgetY(parentWidget) + widget.getY() - parentWidget.getScrollY())
				finalX
			} else {

				getBoundInfo(widget).y
			}
		}

		fun getItemsRects(widget: Widget): MutableList<Rectangle> {
			val items = mutableListOf<Rectangle>()
			val columns = widget.getWidth()
			val rows = widget.getHeight()
			val baseX = getWidgetX(widget)
			val baseY = getWidgetY(widget)

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
