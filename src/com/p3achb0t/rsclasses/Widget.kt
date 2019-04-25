package com.p3achb0t.rsclasses

import com.p3achb0t.Main
import com.p3achb0t.reflectionutils.getIndexFromReflectedArray
import com.p3achb0t.reflectionutils.getWidgetData
import jdk.internal.org.objectweb.asm.tree.ClassNode
import java.awt.Rectangle

class Widget : RSClasses {
    var x = 0
    var y = 0
    var scrollX = 0
    var scrollY = 0
    var id = 0
    var parentId = 0
    var height = 0
    var width = 0
    var name = ""
    var text = ""
    var type = ""
    var borderThickness = ""
    var actionType = ""
    var actions = ""
    var boundsIndex = 0
    var childTextureId = ""
    var children = ""
    var componentIndex = 0
    var disabledMediaId = 0
    var disabledMediaType = ""
    var dynamicValue = ""
    var enabledMediaId = ""
    var enabledMediaType = 0
    var hidden = ""
    var itemId = 0
    var itemStackSize = 0
    var loopCycle = 0
    var parent = ""
    var scrollMax = 0
    var selectedAction = ""
    var shadowColor = ""
    var slotIds = ""
    var slotStackSizes = ""
    var spell = ""
    var spriteId = ""
    var textColor = ""
    var textureId = ""
    var tooltip = ""
    override fun analyze(node: ClassNode, rsClassesMap: Map<String, RSClasses>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getParentIndex(): WidgetIndex {
        val parentIndex = parentId.shr(16)
        val childIndex = parentId.and(0xFF)
        return WidgetIndex(parentIndex.toString(), childIndex.toString())
    }

    private fun getwidgetX(): Int {
        return if (parentId > 0) {
            val parentIndex = getParentIndex()
            val parentWidget = getWidgetData(parentIndex)
            parentWidget.getwidgetX() + x - scrollX
        } else {
            getBoundInfo().x
        }
    }

    private fun getwidgetY(): Int {
        return if (parentId > 0) {
            val parentIndex = getParentIndex()
            val parentWidget = getWidgetData(parentIndex)
            parentWidget.getwidgetY() + y - scrollY
        } else {
            getBoundInfo().y
        }
    }

    fun getItemsRects(): MutableList<Rectangle> {
        val items = mutableListOf<Rectangle>()
        val columns = this.width
        val rows = this.height
        val baseX = getwidgetX() + x
        val baseY = getwidgetY() + y

        for (i in 0 until (columns * rows)) {
            val row = i / columns
            val col = i % columns
            val _x = baseX + ((32 + 10) * col)
            val _y = baseY + ((32 + 4) * row)
            items.add(Rectangle(_x, _y, 32, 32))
        }
        return items
    }

    fun getDrawableRect(): Rectangle {
        return Rectangle(getwidgetX(), getwidgetY(), width, height)
    }

    private fun getBoundInfo(): Rectangle {
        if (boundsIndex >= 0) {
            val clazz = Main.client!!::class.java
            val widgetBoundXField = clazz.getDeclaredField(
                Main.dream?.analyzers?.get(
                    Client::class.java.simpleName
                )?.normalizedFields?.get("widgetBoundsX")?.obsName
            )
            val widgetX = getIndexFromReflectedArray(boundsIndex, Main.client!!, widgetBoundXField)

            val widgetBoundYField = clazz.getDeclaredField(
                Main.dream?.analyzers?.get(
                    Client::class.java.simpleName
                )?.normalizedFields?.get("widgetBoundsY")?.obsName
            )
            val widgetY = getIndexFromReflectedArray(boundsIndex, Main.client!!, widgetBoundYField)

            val widgetHeightsField = clazz.getDeclaredField(
                Main.dream?.analyzers?.get(
                    Client::class.java.simpleName
                )?.normalizedFields?.get("widgetHeights")?.obsName
            )
            val widgetHeight = getIndexFromReflectedArray(boundsIndex, Main.client!!, widgetHeightsField)

            val widgetWidthsField = clazz.getDeclaredField(
                Main.dream?.analyzers?.get(
                    Client::class.java.simpleName
                )?.normalizedFields?.get("widgetWidths")?.obsName
            )
            val widgetWidth = getIndexFromReflectedArray(boundsIndex, Main.client!!, widgetWidthsField)


            return Rectangle(widgetX.toInt(), widgetY.toInt(), widgetWidth.toInt(), widgetHeight.toInt())
        } else {
            return Rectangle(-1, -1, -1, -1)
        }
    }

    override fun toString(): String {
        var result = "ID: $id\n"
        result += "parentId: $parentId\n"

        if (parentId > 0) {
            val parentIndex = parentId.shr(16)
            val childIndex = parentId.and(0xFF)
            result += "\tparent Index:($parentIndex,$childIndex) \n"
        }
        result += "type: $type\n"
        result += "x: $x\n"
        result += "y: $y\n"
        result += "height: $height\n"
        result += "width: $width\n"
        result += "scrollX: $scrollX\n"
        result += "scrollY: $scrollY\n"
        result += "scrollMax: $scrollMax\n"
        result += "text: $text\n"
        result += "name: $name\n"
        result += "borderThickness: $borderThickness\n"
        result += "children: $children\n"
        result += "hidden: $hidden\n"
        result += "boundsIndex: $boundsIndex\n"
        result += "boundRect: ${getBoundInfo()}"

        return result
    }

    constructor()
    constructor(fields: MutableMap<String, Field>) : super() {

        this.fields = fields
        x = fields["x"]?.value?.toInt() ?: -1
        y = fields["y"]?.value?.toInt() ?: -1
        scrollX = fields["scrollX"]?.value?.toInt() ?: -1
        scrollY = fields["scrollY"]?.value?.toInt() ?: -1
        id = fields["id"]?.value?.toInt() ?: -1
        parentId = fields["parentId"]?.value?.toInt() ?: -1
        height = fields["height"]?.value?.toInt() ?: -1
        width = fields["width"]?.value?.toInt() ?: -1
        width = fields["width"]?.value?.toInt() ?: -1
        type = fields["type"]?.value.toString()
        name = fields["name"]?.value.toString()
        borderThickness = fields["borderThickness"]?.value.toString()

        actionType = fields["actionType"]?.value.toString()
        actions = fields["actions"]?.value.toString()
        boundsIndex = fields["boundsIndex"]?.value?.toInt() ?: -1
        childTextureId = fields["childTextureId"]?.value.toString()
        children = fields["children"]?.value.toString()
        componentIndex = fields["componentIndex"]?.value?.toInt() ?: -1
        disabledMediaId = fields["disabledMediaId"]?.value?.toInt() ?: -1
        disabledMediaType = fields["disabledMediaType"]?.value.toString()
        dynamicValue = fields["dynamicValue"]?.value.toString()
        enabledMediaId = fields["enabledMediaId"]?.value.toString()
        enabledMediaType = fields["enabledMediaType"]?.value?.toInt() ?: -1
        hidden = fields["hidden"]?.value.toString()
        id = fields["id"]?.value?.toInt() ?: -1
        itemId = fields["itemId"]?.value?.toInt() ?: -1
        itemStackSize = fields["itemStackSize"]?.value?.toInt() ?: -1
        loopCycle = fields["loopCycle"]?.value?.toInt() ?: -1
        name = fields["name"]?.value.toString()
        parent = fields["parent"]?.value.toString()
        scrollMax = fields["scrollMax"]?.value?.toInt() ?: -1
        scrollX = fields["scrollX"]?.value?.toInt() ?: -1
        scrollY = fields["scrollY"]?.value?.toInt() ?: -1
        selectedAction = fields["selectedAction"]?.value.toString()
        shadowColor = fields["shadowColor"]?.value.toString()
        slotIds = fields["slotIds"]?.value.toString()
        slotStackSizes = fields["slotStackSizes"]?.value.toString()
        spell = fields["spell"]?.value.toString()
        spriteId = fields["spriteId"]?.value.toString()
        text = fields["text"]?.value.toString()
        textColor = fields["textColor"]?.value.toString()
        textureId = fields["textureId"]?.value.toString()
        tooltip = fields["tooltip"]?.value.toString()
        type = fields["type"]?.value.toString()

    }


}

data class WidgetIndex(var parentID: String, var childID: String)