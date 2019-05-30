package com.p3achb0t.hook_interfaces

interface PlayerComposite {
    fun get_animatedModelID(): Int
    fun get_appearance(): ArrayList<Int>
    fun get_bodyColors(): ArrayList<Int>
    fun get_female(): Boolean
    fun get_npcID(): Int
    fun get_staticModelID(): Int
}
