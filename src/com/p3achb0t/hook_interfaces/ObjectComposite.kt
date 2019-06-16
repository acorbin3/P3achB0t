package com.p3achb0t.hook_interfaces

interface ObjectComposite : CacheNode {
    fun getActions(): Array<String>
    fun getName(): String
}
