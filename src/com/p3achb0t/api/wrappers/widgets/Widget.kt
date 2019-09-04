package com.p3achb0t.api.wrappers.widgets

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t._runestar_interfaces.InterfaceParent
import java.awt.Rectangle

interface Widget {
	data class WidgetIndex(var parentID: String, var childID: String, var raw: Int = 0)
	companion object {

		fun getParentIndex(widget: Component, client: com.p3achb0t._runestar_interfaces.Client): WidgetIndex {
			val parentIndex = widget.getParentId().shr(16)
			val childIndex = widget.getParentId().and(0xFFFF)
			if (parentIndex == -1) {
				val containerIndex = widget.getId().shr(16)
				val hTable = client.getInterfaceParents()
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

		fun getChainedParentIndex(widget: Component, parentList: ArrayList<WidgetIndex>, client: Client): ArrayList<WidgetIndex> {
			val parentIndex = getParentIndex(widget, client)
			return if (parentIndex.parentID.toInt() != -1) {
				parentList.add(parentIndex)
				val parent = getWidget(parentIndex,client )
				if (parent != null) {
					getChainedParentIndex(parent, parentList, client)
				} else {
					parentList
				}
			} else {
				parentList
			}
		}

		private fun getBoundInfo(widget: Component, client: Client): Rectangle {
			return if (widget.getRootIndex() >= 0) {
				val widgetX = client.getRootComponentXs()[widget.getRootIndex()]
				val widgetY = client.getRootComponentYs()[widget.getRootIndex()]
				val widgetHeight = client.getRootComponentHeights()[widget.getRootIndex()]
				val widgetWidth = client.getRootComponentWidths()[widget.getRootIndex()]
				Rectangle(widgetX, widgetY, widgetWidth, widgetHeight)
			} else {
				Rectangle(0, 0, 0, 0)
			}
		}

		fun getDrawableRect(widget: Component, client: Client): Rectangle {
			val boundsWidth = widget.getWidth()
			val boundsHeight = widget.getHeight()
			val rect = Rectangle(
				getWidgetX(widget, client),
				getWidgetY(widget, client), boundsWidth, boundsHeight
			)
			return rect
		}

		fun getWidget(index: WidgetIndex, client: Client): Component? {
			var component: Component? = null
			try {
				component = client.getInterfaceComponents()[index.parentID.toInt()][index.childID.toInt()]
			} catch (e: Exception) {
			}
			return component
		}

		fun getWidgetX(widget: Component, client: Client): Int {
			return if (getParentIndex(widget, client).parentID.toInt() != -1) {
				val parentIndex = getParentIndex(widget, client)
				val parentWidget = getWidget(parentIndex, client)
				if (parentWidget != null) {
					(getWidgetX(parentWidget, client) + widget.getX() - parentWidget.getScrollX())
				} else {
					widget.getX()
				}
			} else {

				getBoundInfo(widget, client).x
			}
		}

		fun getWidgetY(widget: Component, client: Client): Int {

			return if (getParentIndex(widget, client).parentID.toInt() != -1) {
				val parentIndex = getParentIndex(widget, client)
				val parentWidget = getWidget(parentIndex, client)
				if (parentWidget != null) {
					(getWidgetY(parentWidget, client) + widget.getY() - parentWidget.getScrollY())
				} else {
					widget.getY()
				}
			} else {

				getBoundInfo(widget, client).y
			}
		}

		fun getItemsRects(widget: Component, client: Client): MutableList<Rectangle> {
			val items = mutableListOf<Rectangle>()
			val columns = widget.getWidth()
			val rows = widget.getHeight()
			val baseX = getWidgetX(widget,client )
			val baseY = getWidgetY(widget,client )

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
