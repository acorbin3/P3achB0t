package com.p3achb0t.hook_interfaces

interface VarpBit : CacheNode {
    fun get_index(): Int
    fun get_leastSig(): Int
    fun get_mostSig(): Int
}
