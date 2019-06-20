package com.p3achb0t.widgetexplorer

import com.p3achb0t.Main
import com.p3achb0t.rsclasses.Client
import com.p3achb0t.rsclasses.RSClasses
import com.p3achb0t.rsclasses.WidgetIndex
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
            treeview<WidgetIndex> {
                root = TreeItem(WidgetIndex("Widgets", "Widgets"))
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
                    //TODO - Update with injection
//                    val currentWidget = getWidgetData(it)
                    //For the parent trees, there is no parent ID
                    if (parentId != "") {
                        //TODO - Update with injection
//                        Main.selectedWidget = currentWidget
                    } else {
                        Main.selectedWidget = null
                    }
//TODO - Update with injection
//                    controller.currentDetail.set(currentWidget.toString())

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
    var allWidgets: ObservableList<WidgetIndex> = FXCollections.observableArrayList()
    val parentWidgetList: ObservableList<WidgetIndex> = FXCollections.observableArrayList()
    var widgetDetails = mutableMapOf<String, String>()//Index will be (parent,child)
    var parentList = emptyList<WidgetIndex>()
    var currentDetail = SimpleStringProperty()

    var allWidgetFileHookData = mutableMapOf<String, MutableMap<String, RSClasses.Field>>()


    fun getUpdatedWidgets() {

        allWidgets.clear()
        val widgetClassName = Main.dream?.analyzers?.get(
            Client::class.java.simpleName
        )?.fields?.find { it.field == "widgets" }?.owner
        val widgetFieldName = Main.dream?.analyzers?.get(
            Client::class.java.simpleName
        )?.fields?.find { it.field == "widgets" }?.name

        val widgetClazz = Main.classLoader?.loadClass(widgetClassName)
        val widgetField = widgetClazz?.getDeclaredField(widgetFieldName)
        widgetField?.isAccessible = true
        val widgetObj = widgetField?.get(null)

        // Get all the widget indexes
        if (widgetObj is Array<*>) {
            widgetObj.forEachIndexed { parentIndex, childArray ->
                if (childArray is Array<*>) {
                    childArray.forEachIndexed { childIndex, childItem ->
                        allWidgets.add(WidgetIndex("Parent $parentIndex", childIndex.toString()))
                        //TODO Get widget items
//                        val v = childItem!!::class.java
//                        var result = ""
//                        val widgetDetailIndex = "$parentIndex,$childIndex"
//                        val currentWidgetFieldHookData = Main.dream?.analyzers?.get(
//                            Widget::class.java.simpleName)?.fields!!
//                        val localWidgetData = mutableMapOf<String,RSClasses.Field>()
//                        for (field in v.declaredFields) {
//                            if (currentWidgetFieldHookData.contains(field.name)) {
////                                println(field.name + " ->" + currentWidgetFieldHookData[field.name])
//                                // Get data
//                                result += currentWidgetFieldHookData[field.name]?.fieldName + " -> "
//                                currentWidgetFieldHookData[field.name]?.resultValue = getFieldResult(v, childItem, field, 0).toString()
//                                result +=  currentWidgetFieldHookData[field.name]?.resultValue + " \n"
//                                localWidgetData[currentWidgetFieldHookData[field.name]!!.fieldName] = RSClasses.Field()
//                                localWidgetData[currentWidgetFieldHookData[field.name]?.fieldName]?.resultValue = currentWidgetFieldHookData[field.name]?.resultValue.toString()
//
//
//                            }
//                        }
//                        allWidgetFileHookData[widgetDetailIndex] = localWidgetData
//                        println("\t $result")

//                        widgetDetails[widgetDetailIndex] = result

                    }
                }
            }
        }
        parentWidgetList.clear()
        parentList = allWidgets
            .map { it.parentID }
            .distinct().map { WidgetIndex("", it) }
        for (item in parentList) {
            parentWidgetList.add(item)
        }
    }

}