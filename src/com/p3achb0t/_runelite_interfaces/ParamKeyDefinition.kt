package com.p3achb0t._runelite_interfaces

interface ParamKeyDefinition : DualNode {
    fun getIsMembersOnly(): Boolean
    fun getKeyInt(): Int
    fun getKeyString(): String
    fun getType(): Char
}
