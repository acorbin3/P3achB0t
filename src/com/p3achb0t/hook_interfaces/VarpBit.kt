package com.p3achb0t.hook_interfaces

interface VarpBit : CacheNode {
    fun getIndex(): Int
    fun getLeastSig(): Int
    fun getMostSig(): Int
}
