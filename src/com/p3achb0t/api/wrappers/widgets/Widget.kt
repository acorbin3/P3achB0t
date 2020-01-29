package com.p3achb0t.api.wrappers.widgets

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t._runestar_interfaces.InterfaceParent
import com.p3achb0t.api.Context
import java.awt.Rectangle

interface Widget {
	data class WidgetIndex(var parentID: String, var childID: String, var raw: Int = 0){
		override fun toString():String{
			return "($parentID,$childID)"
		}
	}
	companion object {

		fun getCurrentIndex(widget: Component): WidgetIndex{
			return WidgetIndex(widget.getId().shr(16).toString(), widget.getId().and(0xFFFF).toString())
		}
		fun getParentIndex(widget: Component, ctx: Context): WidgetIndex {
			val parentIndex = widget.getParentId().shr(16)
			val childIndex = widget.getParentId().and(0xFFFF)
			if (parentIndex == -1) {
				val containerIndex = widget.getId().shr(16)
				val hTable = ctx.client.getInterfaceParents()
				for (node in hTable.getBuckets()) {
					if (node is InterfaceParent && node.getItf() == containerIndex) {
						val parent = node.getKey().toInt().shr(16)
						val child = node.getKey().toInt().and(0xFFFF)
						return WidgetIndex(
							parent.toString(),
							child.toString(),
							node.getItf()
						)

					}
					var next = node.getNext()
					while (next != null && next != node) {
						if (next is InterfaceParent && next.getItf() == containerIndex) {
							val parent = next.getKey().toInt().shr(16)
							val child = next.getKey().toInt().and(0xFFFF)
							return WidgetIndex(
								parent.toString(),
								child.toString(),
								next.getKey().toInt()
							)
						}
						next = next.getNext()
					}
				}
			}
			return WidgetIndex(parentIndex.toString(), childIndex.toString())
		}

		fun getChainedParentIndex(widget: Component, parentList: ArrayList<WidgetIndex>, ctx: Context): ArrayList<WidgetIndex> {
			val parentIndex = getParentIndex(widget, ctx)
			return if (parentIndex.parentID.toInt() != -1) {
				parentList.add(parentIndex)
				val parent = getWidget(parentIndex, ctx)
				if (parent != null) {
					getChainedParentIndex(parent, parentList, ctx)
				} else {
					parentList
				}
			} else {
				parentList
			}
		}

		private fun getBoundInfo(widget: Component, ctx: Context): Rectangle {
			return if (widget.getRootIndex() >= 0) {
				val widgetX = ctx.client.getRootComponentXs()[widget.getRootIndex()]
				val widgetY = ctx.client.getRootComponentYs()[widget.getRootIndex()]
				val widgetHeight = ctx.client.getRootComponentHeights()[widget.getRootIndex()]
				val widgetWidth = ctx.client.getRootComponentWidths()[widget.getRootIndex()]
				Rectangle(widgetX, widgetY, widgetWidth, widgetHeight)
			} else {
				Rectangle(0, 0, 0, 0)
			}
		}

		fun getDrawableRect(widget: Component, ctx: Context): Rectangle {
			val boundsWidth = widget.getWidth()
			val boundsHeight = widget.getHeight()
			val rect = Rectangle(
				getWidgetX(widget, ctx),
				getWidgetY(widget, ctx), boundsWidth, boundsHeight
			)
			return rect
		}

		fun getWidget(index: WidgetIndex, ctx: Context): Component? {
			var component: Component? = null
			try {
				component = ctx.client.getInterfaceComponents()[index.parentID.toInt()][index.childID.toInt()]
			} catch (e: Exception) {
			}
			return component
		}

		fun getWidgetX(widget: Component, ctx: Context): Int {
			return if (getParentIndex(widget, ctx).parentID.toInt() != -1) {
				val parentIndex = getParentIndex(widget, ctx)
				val parentWidget = getWidget(parentIndex, ctx)
				if (parentWidget != null) {
					(getWidgetX(parentWidget, ctx) + widget.getX() - parentWidget.getScrollX())
				} else {
					widget.getX()
				}
			} else {

				getBoundInfo(widget, ctx).x
			}
		}

		fun getWidgetY(widget: Component, ctx: Context): Int {

			return if (getParentIndex(widget, ctx).parentID.toInt() != -1) {
				val parentIndex = getParentIndex(widget, ctx)
				val parentWidget = getWidget(parentIndex, ctx)
				if (parentWidget != null) {
					(getWidgetY(parentWidget, ctx) + widget.getY() - parentWidget.getScrollY())
				} else {
					widget.getY()
				}
			} else {

				getBoundInfo(widget, ctx).y
			}
		}

		fun getItemsRects(widget: Component, ctx: Context): MutableList<Rectangle> {
			val items = mutableListOf<Rectangle>()
			val columns = widget.getWidth()
			val rows = widget.getHeight()
			val baseX = getWidgetX(widget, ctx)
			val baseY = getWidgetY(widget, ctx)

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
}
