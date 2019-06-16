package com.p3achb0t.hook_interfaces

interface ItemComposite : CacheNode {
    fun getGroundActions(): Array<String>
    fun getItemComposite_id(): Int
    fun getMember(): Boolean
    fun getName(): String
    fun getWidgetActions(): Array<String>
}
