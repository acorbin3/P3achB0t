package com.p3achb0t.hook_interfaces

interface Item : Renderable {
    fun get_itemID(): Int
    fun get_stackSize(): Int
}
