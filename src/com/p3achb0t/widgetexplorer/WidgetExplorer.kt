package com.p3achb0t.widgetexplorer

import com.p3achb0t.Main
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
        }
        center = vbox {
            label("Parent Widgets")
            treeview<Widget.WidgetIndex> {
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
                    controller.currentDetail.set(currentWidget?.let { it1 -> controller.getWidgetDetails(it1) })

                }

            }
        }
        right = vbox {
            label("Details")
            text("Text").textProperty().bindBidirectional(controller.currentDetail)
//            controller.currentDetail.addListener(ChangeListener { observable, oldValue, newValue ->
//                text(newValue)
//                println(newValue)
//            })
        }


    }
}

class WidgetController : Controller() {
    var allWidgets: ObservableList<Widget.WidgetIndex> = FXCollections.observableArrayList()
    val parentWidgetList: ObservableList<Widget.WidgetIndex> = FXCollections.observableArrayList()
    var widgetDetails = mutableMapOf<String, String>()//Index will be (parent,id)
    var parentList = emptyList<Widget.WidgetIndex>()
    var currentDetail = SimpleStringProperty()

    var allWidgetFileHookData = mutableMapOf<String, Widget>()


    fun getUpdatedWidgets() {

        allWidgets.clear()

        // Get all the widget indexes
        val widgets = Main.clientData.getWidgets()
        widgets.forEachIndexed { parentIndex, childArray ->
            if (childArray != null) {
                childArray.forEachIndexed { childIndex, childItem ->
                    if (childItem != null) {
                        allWidgets.add(Widget.WidgetIndex("Parent $parentIndex", childIndex.toString()))
                        val widgetDetailIndex = "$parentIndex,$childIndex"
                        allWidgetFileHookData[widgetDetailIndex] = childItem
                        val result = getWidgetDetails(childItem)
                        println("\t $result")
                        widgetDetails[widgetDetailIndex] = result
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

    fun getWidgetDetails(widget: Widget): String {
        var result = ""
        try {
            result += "Widget ID:" + widget.getWidget_id() + "\n"
            result += "Text:" + widget.getText() + "\n"
            var actions = "["
            if (widget.getActions() != null) {
                widget.getActions().iterator().forEach { actions += "$it," }
            }
            result += "Actions:$actions]\n"
            result += "Child Texture ID:" + widget.getChildTextureId() + "\n"
            result += "Item ID:" + widget.getItemId() + "\n"
            result += "Component Index:" + widget.getComponentIndex() + "\n"
            result += "Heigth:" + widget.getHeight() + "\n"
            result += "Width:" + widget.getWidth() + "\n"
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
            result += "Children: ${widget.getChildren().size}"
            if (widget.getChildren().isNotEmpty()) {
                widget.getChildren().iterator().forEach { result += getWidgetDetails(it) }
            }
        } catch (e: Exception) {
            return result
        }
        return result
    }

}