package com.p3achb0t.hook_interfaces

interface NpcComposite : CacheNode {
    fun getActions(): Array<String>
    fun getNpcComposite_id(): Int
    fun getName(): String
}
