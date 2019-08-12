package com.p3achb0t.widgetexplorer

import com.p3achb0t.MainApplet
import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.wrappers.Client
import com.p3achb0t.api.wrappers.Items
import com.p3achb0t.api.wrappers.widgets.doesWidgetContainText
import com.p3achb0t.hook_interfaces.Widget
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TreeItem
import tornadofx.*

class WidgetExplorer : View() {
    private val controller: WidgetController by inject()

    override val root = borderpane {

        top = vbox {
            label("Widget Explorer")
            button("Refresh") {
                action {
                    //TODO - go get the latest parents and children of selected
                    println("Pressed button")
                    controller.getUpdatedWidgets()
                }
            }
            form {
                fieldset("Search") {
                    field("Search") {
                        textfield {
                            action {
                                println("Filtering on: +${this.text}")
                                controller.getUpdatedWidgets(this.text)
                            }
                        }


                    }

                }
            }
        }
        left = vbox {
            label("Parent Widgets")
            treeview<Widget.WidgetIndex> {
                this.maxWidth = 150.0

                root = TreeItem(Widget.WidgetIndex("Widgets", "Widgets"))
                cellFormat { text = it.childID }
                populate { parent ->
                    if (parent == root)
                        controller.parentWidgetList
                    else
                        controller.allWidgets.filter { it.parentID == parent.value.childID }
                }
                onUserSelect {
                    val parentId = it.parentID.replace("Parent ", "")
                    val childID = it.childID.replace("Parent ", "")
                    println("($parentId,$childID)")
                    val widgetDetailIndex = "$parentId,$childID"
                    println(controller.widgetDetails[widgetDetailIndex])
                    val currentWidget = controller.allWidgetFileHookData[widgetDetailIndex]
                    //For the parent trees, there is no parent ID
                    if (parentId != "") {
                        MainApplet.selectedWidget = currentWidget
                    } else {
                        MainApplet.selectedWidget = null
                    }
                    controller.currentDetail.set(currentWidget?.let { it1 -> controller.getWidgetDetails(it1, 0) })
                    Items.dumpItems()

                }

            }
        }
        center = vbox {
            label("Details")
            scrollpane { text("Text").textProperty().bindBidirectional(controller.currentDetail) }

        }


    }
}

class WidgetController : Controller() {
    var allWidgets: ObservableList<Widget.WidgetIndex> = FXCollections.observableArrayList()
    val parentWidgetList: ObservableList<Widget.WidgetIndex> = FXCollections.observableArrayList()
    var widgetDetails = mutableMapOf<String, String>()//Index will be (parent,widgetID)
    var parentList = emptyList<Widget.WidgetIndex>()
    var currentDetail = SimpleStringProperty()

    var allWidgetFileHookData = mutableMapOf<String, Component>()


    fun getUpdatedWidgets(filter: String = "") {

        allWidgets.clear()

        // Get all the widget indexes
        val widgets = Client.client.getInterfaceComponents()
        widgets.forEachIndexed { parentIndex, childArray ->
            if (childArray != null) {
                childArray.forEachIndexed { childIndex, childItem ->
                    if (childItem != null) {
                        var add = true
                        if (filter.isNotEmpty()) {
                            add = doesWidgetContainText(childItem, filter)
                        }
                        if (add) {
                            allWidgets.add(Widget.WidgetIndex("Parent $parentIndex", childIndex.toString()))
                            val widgetDetailIndex = "$parentIndex,$childIndex"
                            allWidgetFileHookData[widgetDetailIndex] = childItem
                            val result = getWidgetDetails(childItem, 0)
                            println("\t $result")
                            widgetDetails[widgetDetailIndex] = result
                        }
                    }
                }
            }
        }
        parentWidgetList.clear()
        parentList = allWidgets
            .map { it.parentID }
            .distinct().map { Widget.WidgetIndex("", it) }
        for (item in parentList) {
            parentWidgetList.add(item)
        }
    }


    fun getWidgetDetails(widget: Component, index: Int): String {
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
            if (widget.getOps() != null) {
                widget.getOps().iterator().forEach { actions += "$it," }
            }
            result += "Actions:$actions]\n"
            result += "getSpriteId2:" + widget.getSpriteId2() + "\n"
            result += "Item ID:" + widget.getItemId() + "\n"
            result += "Component Index:" + widget.getChildIndex() + "\n"
            result += "Raw Position: ${widget.getX()},${widget.getY()}\n"
            result += "Position: ${Widget.getWidgetX(widget)},${Widget.getWidgetY(widget)}\n"
            result += "Scroll(x,y) ${widget.getScrollX()},${widget.getScrollY()}\n"
            result += "Scroll max: ${widget.getScrollHeight()}\n"
            result += "Height:" + widget.getHeight() + "\n"
            result += "Width:" + widget.getWidth() + "\n"
            result += "StackCount" + widget.getItemQuantity() + "\n"
            result += "SpriteID:" + widget.getSpriteId() + "\n"
            result += "Shadow Color: " + widget.getSpriteShadow() + "\n"
            result += "getButtonType: " + widget.getButtonType() + "\n"
            result += "EnabledMediaId: " + widget.getModelId2() + "\n"
            result += "EnabledMediaType: " + widget.getModelType2() + "\n"
            result += "DisabledMediaId: " + widget.getModelId() + "\n"
            result += "DisabledMediaType: " + widget.getModelType() + "\n"
            result += "Hidden: " + widget.getIsHidden() + "\n"
            result += "TextureId: " + widget.getSpriteId2() + "\n"
            result += "Tooltip: " + widget.getButtonText() + "\n"
            result += "SelectedAction: " + widget.getTargetVerb() + "\n"
            result += "Text Color: " + widget.getColor().toString() + "\n"
            result += "Boarder Thickness ${widget.getOutline()} \n"
            result += "Spell: ${widget.getSpellName()}\n"
            result += "Dynamic Values: ["
            try {
                for (i in widget.getCs1Instructions()) {
                    result += "$i,"
                }
            } catch (e: Exception) {
            }
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
            result += "Bounds Index: ${widget.getParentId()}\n"
            result += "Bounds x: ["
            for (i in Client.client.getRootComponentXs()) {
                result += "$i,"
            }
            result += "]\n"

            result += "Bounds y: ["
            for (i in Client.client.getRootComponentYs()) {
                result += "$i,"
            }
            result += "]\n"

            result += "Bounds Width: ["
            for (i in Client.client.getRootComponentWidths()) {
                result += "$i,"
            }
            result += "]\n"

            result += "Bounds Height: ["
            for (i in Client.client.getRootComponentHeights()) {
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