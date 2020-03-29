package com.p3achb0t.api.interfaces

interface Inventory : Node {
    fun getIds(): IntArray
    fun getQuantities(): IntArray
}
