package com.p3achb0t.widgetexplorer

import com.p3achb0t.downloader.Main
import com.p3achb0t.rsclasses.Client
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
        }
        left = vbox {
            label("Parent Widgets")
            treeview<WidgetData> {
                root = TreeItem(WidgetData("Widgets", "Widgets"))
                cellFormat { text = it.childID }
                populate { parent ->
                    if (parent == root)
                        controller.parentWidgetList
                    else
                        controller.allWidgets.filter { it.parentID == parent.value.childID }
                }
                onUserSelect {
                    val parentId = it.parentID.replace("Parent ", "")
                    println("(" + parentId + "," + it.childID + ")")
                }

            }
        }
        right = vbox {
            label("Details")
            text("Details of widget")
        }


    }
}

data class WidgetData(var parentID: String, var childID: String)

class WidgetController : Controller() {
    var allWidgets: ObservableList<WidgetData> = FXCollections.observableArrayList()
    val parentWidgetList: ObservableList<WidgetData> = FXCollections.observableArrayList()
    var widgetDetails = mutableMapOf<String, String>()//Index will be (parent,child)
    var parentList = emptyList<WidgetData>()

    fun getUpdatedWidgets() {
        allWidgets.clear()
        val widgetClassName = Main.dream?.analyzers?.get(
            Client::class.java.simpleName
        )?.normalizedFields?.get("widgets")?.fieldTypeObsName
        val widgetFieldName = Main.dream?.analyzers?.get(
            Client::class.java.simpleName
        )?.normalizedFields?.get("widgets")?.obsName

        val widgetClazz = Main.classLoader?.loadClass(widgetClassName)
        val widgetField = widgetClazz?.getDeclaredField(widgetFieldName)
        widgetField?.isAccessible = true
        val widgetObj = widgetField?.get(null)
        if (widgetObj is Array<*>) {
            widgetObj.forEachIndexed { parentIndex, childArray ->
                if (childArray is Array<*>) {
                    childArray.forEachIndexed { index, childItem ->
                        allWidgets.add(WidgetData("Parent $parentIndex", index.toString()))
                        //TODO Get widget items
                        val v = childItem!!::class.java
                        for (field in v.declaredFields) {
                            println(field.name)
                        }

                    }
                }
            }
        }
        parentWidgetList.clear()
        parentList = allWidgets
            .map { it.parentID }
            .distinct().map { WidgetData("", it) }
        for (item in parentList) {
            parentWidgetList.add(item)
        }
    }

}