package com.p3achb0t.widgetexplorer

import com.p3achb0t.Main
import com.p3achb0t.api.wrappers.Items
import com.p3achb0t.hook_interfaces.Widget
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TreeItem
import tornadofx.*

//This class is used to create a widget explorer to find widgets
// To lanch the widget explorer you just
// 1. Create an app object/class:// class MyApp : App(WidgetExplorer::class)
// 2. Then need to call: //    launch<MyApp>(args)

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
                        Main.selectedWidget = currentWidget
                    } else {
                        Main.selectedWidget = null
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

    var allWidgetFileHookData = mutableMapOf<String, Widget>()


    fun getUpdatedWidgets(filter: String = "") {

        allWidgets.clear()

        // Get all the widget indexes
        val widgets = Main.clientData.getWidgets()
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

    fun getStrippedWidgetDetails(widget: Widget): String {
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

            widget.getChildren().iterator().forEach { result += getStrippedWidgetDetails(it) }
        } catch (e: Exception) {
            return result
        }
        return result
    }

    fun doesWidgetContainText(widget: Widget, filter: String): Boolean {
        val text = getStrippedWidgetDetails(widget)
        return text.contains(filter)
    }

    fun getWidgetDetails(widget: Widget, index: Int): String {
        var result = "--$index--\n"
        try {
            val containerID = widget.getWidget_id().shr(16)
            val childID = widget.getWidget_id().and(0xFF)
            result += "Widget ID:" + widget.getWidget_id() + "($containerID,$childID)\n"
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
            if (widget.getActions() != null) {
                widget.getActions().iterator().forEach { actions += "$it," }
            }
            result += "Actions:$actions]\n"
            result += "Child Texture ID:" + widget.getChildTextureId() + "\n"
            result += "Item ID:" + widget.getItemId() + "\n"
            result += "Component Index:" + widget.getComponentIndex() + "\n"
            result += "Raw Position: ${widget.getX()},${widget.getY()}\n"
            result += "Position: ${Widget.getWidgetX(widget)},${Widget.getWidgetY(widget)}\n"
            result += "Scroll(x,y) ${widget.getScrollX()},${widget.getScrollY()}\n"
            result += "Scroll max: ${widget.getScrollMax()}\n"
            result += "Height:" + widget.getHeight() + "\n"
            result += "Width:" + widget.getWidth() + "\n"
            result += "StackCount" + widget.getItemStackSize() + "\n"
            result += "SpriteID:" + widget.getSpriteId() + "\n"
            result += "Shadow Color: " + widget.getShadowColor() + "\n"
            result += "Action Type: " + widget.getActionType() + "\n"
            result += "EnabledMediaId: " + widget.getEnabledMediaId() + "\n"
            result += "EnabledMediaType: " + widget.getEnabledMediaType() + "\n"
            result += "DisabledMediaId: " + widget.getDisabledMediaId() + "\n"
            result += "DisabledMediaType: " + widget.getDisabledMediaType() + "\n"
            result += "Hidden: " + widget.getHidden() + "\n"
            result += "TextureId: " + widget.getTextureId() + "\n"
            result += "Tooltip: " + widget.getTooltip() + "\n"
            result += "SelectedAction: " + widget.getSelectedAction() + "\n"
            result += "Text Color: " + widget.getTextColor().toString() + "\n"
            result += "Boarder Thickness ${widget.getBorderThickness()} \n"
            result += "Spell: ${widget.getSpell()}\n"
            result += "Dynamic Values: ["
            try {
                for (i in widget.getDynamicValue()) {
                    result += "$i,"
                }
            } catch (e: Exception) {
            }
            result += "]\n"
            if (widget.getSlotIds() != null) {
                result += "Slot IDs:"
                widget.getSlotIds().iterator().forEach { result += "$it,\t" }
                result += "]\n"
            }

            if (widget.getSlotStackSizes() != null) {
                result += "Slot Stack sizes:"
                widget.getSlotStackSizes().iterator().forEach { result += "$it,\t" }
                result += "]\n"
            }
            result += "---Internal---\n"
            result += "Bounds Index: ${widget.getBoundsIndex()}\n"
            result += "Bounds x: ["
            for (i in Main.clientData.getWidgetBoundsX()) {
                result += "$i,"
            }
            result += "]\n"

            result += "Bounds y: ["
            for (i in Main.clientData.getWidgetBoundsY()) {
                result += "$i,"
            }
            result += "]\n"

            result += "Bounds Width: ["
            for (i in Main.clientData.getWidgetWidths()) {
                result += "$i,"
            }
            result += "]\n"

            result += "Bounds Height: ["
            for (i in Main.clientData.getWidgetHeights()) {
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