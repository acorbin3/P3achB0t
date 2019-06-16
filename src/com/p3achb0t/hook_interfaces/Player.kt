package com.p3achb0t.hook_interfaces

interface Player : Actor {
    fun getComposite(): PlayerComposite
    fun getHidden(): Boolean
    fun getLevel(): Int
    fun getModel(): Model
    fun getName(): NameComposite
    fun getOverheadIcon(): Int
    fun getSkullIcon(): Int
    fun getStandingStill(): Boolean
    fun getTeam(): Int
}
