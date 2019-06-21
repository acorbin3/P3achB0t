package com.p3achb0t.rsclasses

import org.objectweb.asm.tree.ClassNode

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

        return result
    }

    constructor()
    constructor(fields: MutableMap<String, Field>) : super() {

        this.fields = fields
        x = fields["x"]?.resultValue?.toInt() ?: -1
        y = fields["y"]?.resultValue?.toInt() ?: -1
        scrollX = fields["scrollX"]?.resultValue?.toInt() ?: -1
        scrollY = fields["scrollY"]?.resultValue?.toInt() ?: -1
        id = fields["id"]?.resultValue?.toInt() ?: -1
        parentId = fields["parentId"]?.resultValue?.toInt() ?: -1
        height = fields["height"]?.resultValue?.toInt() ?: -1
        width = fields["width"]?.resultValue?.toInt() ?: -1
        width = fields["width"]?.resultValue?.toInt() ?: -1
        type = fields["type"]?.resultValue.toString()
        name = fields["name"]?.resultValue.toString()
        borderThickness = fields["borderThickness"]?.resultValue.toString()

        actionType = fields["actionType"]?.resultValue.toString()
        actions = fields["actions"]?.resultValue.toString()
        boundsIndex = fields["boundsIndex"]?.resultValue?.toInt() ?: -1
        childTextureId = fields["childTextureId"]?.resultValue.toString()
        children = fields["children"]?.resultValue.toString()
        componentIndex = fields["componentIndex"]?.resultValue?.toInt() ?: -1
        disabledMediaId = fields["disabledMediaId"]?.resultValue?.toInt() ?: -1
        disabledMediaType = fields["disabledMediaType"]?.resultValue.toString()
        dynamicValue = fields["dynamicValue"]?.resultValue.toString()
        enabledMediaId = fields["enabledMediaId"]?.resultValue.toString()
        enabledMediaType = fields["enabledMediaType"]?.resultValue?.toInt() ?: -1
        hidden = fields["hidden"]?.resultValue.toString()
        id = fields["id"]?.resultValue?.toInt() ?: -1
        itemId = fields["itemId"]?.resultValue?.toInt() ?: -1
        itemStackSize = fields["itemStackSize"]?.resultValue?.toInt() ?: -1
        loopCycle = fields["loopCycle"]?.resultValue?.toInt() ?: -1
        name = fields["name"]?.resultValue.toString()
        parent = fields["parent"]?.resultValue.toString()
        scrollMax = fields["scrollMax"]?.resultValue?.toInt() ?: -1
        scrollX = fields["scrollX"]?.resultValue?.toInt() ?: -1
        scrollY = fields["scrollY"]?.resultValue?.toInt() ?: -1
        selectedAction = fields["selectedAction"]?.resultValue.toString()
        shadowColor = fields["shadowColor"]?.resultValue.toString()
        slotIds = fields["slotIds"]?.resultValue.toString()
        slotStackSizes = fields["slotStackSizes"]?.resultValue.toString()
        spell = fields["spell"]?.resultValue.toString()
        spriteId = fields["spriteId"]?.resultValue.toString()
        text = fields["text"]?.resultValue.toString()
        textColor = fields["textColor"]?.resultValue.toString()
        textureId = fields["textureId"]?.resultValue.toString()
        tooltip = fields["tooltip"]?.resultValue.toString()
        type = fields["type"]?.resultValue.toString()

    }


}

