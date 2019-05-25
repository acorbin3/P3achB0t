package com.p3achb0t.hook_interfaces

interface ItemComposite : CacheNode {
    fun get_groundActions(): Any
    fun get_itemCompositeID(): Int
    fun get_member(): Any
    fun get_name(): Any
    fun get_widgetActions(): Any
}
