package com.p3achb0t.hook_interfaces

interface NpcComposite : CacheNode {
    fun get_actions(): Array<String>
    fun get_npcID(): Int
    fun get_name(): String
}
