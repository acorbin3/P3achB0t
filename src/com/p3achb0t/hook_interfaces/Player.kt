package com.p3achb0t.hook_interfaces

interface Player : Actor {
    fun get_composite(): Any
    fun get_hidden(): Any
    fun get_level(): Int
    fun get_model(): Any
    fun get_name(): Any
    fun get_overheadIcon(): Int
    fun get_skullIcon(): Int
    fun get_standingStill(): Any
    fun get_team(): Int
}
