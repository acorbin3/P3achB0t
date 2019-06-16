package com.p3achb0t.hook_interfaces

interface CacheNode : Node {
    fun getCacheNode_next(): CacheNode
    fun getCacheNode_previous(): CacheNode
}
