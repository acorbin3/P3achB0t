package com.p3achb0t.hook_interfaces


interface Player : Actor {
//    fun get_composite(): PlayerComposite
    fun get_hidden(): Boolean
    fun get_level(): Int
//    fun get_model(): Model
fun get_name(): NameComposite

    fun get_overheadIcon(): Int
    fun get_skullIcon(): Int
    fun get_standingStill(): Boolean
    fun get_team(): Int
}
