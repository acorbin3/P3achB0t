package com.p3achb0t.hook_interfaces

interface Widget : Node {
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
