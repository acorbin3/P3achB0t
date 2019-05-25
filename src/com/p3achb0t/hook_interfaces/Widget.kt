package com.p3achb0t.hook_interfaces

interface Widget : Node {
    fun get_actionType(): Int
    fun get_actions(): Any
    fun get_borderThickness(): Int
    fun get_boundsIndex(): Int
    fun get_childTextureId(): Int
    fun get_children(): Any
    fun get_componentIndex(): Int
    fun get_disabledMediaId(): Int
    fun get_disabledMediaType(): Int
    fun get_dynamicValue(): Any
    fun get_enabledMediaId(): Int
    fun get_enabledMediaType(): Int
    fun get_height(): Int
    fun get_hidden(): Any
    fun get_widgetID(): Int
    fun get_itemId(): Int
    fun get_itemStackSize(): Int
    fun get_loopCycle(): Int
    fun get_name(): Any
    fun get_parent(): Any
    fun get_parentId(): Int
    fun get_scrollMax(): Int
    fun get_scrollX(): Int
    fun get_scrollY(): Int
    fun get_selectedAction(): Any
    fun get_shadowColor(): Int
    fun get_slotIds(): Any
    fun get_slotStackSizes(): Any
    fun get_spell(): Any
    fun get_spriteId(): Int
    fun get_text(): Any
    fun get_textColor(): Int
    fun get_textureId(): Int
    fun get_tooltip(): Any
    fun get_type(): Int
    fun get_width(): Int
    fun get_x(): Int
    fun get_y(): Int
}
