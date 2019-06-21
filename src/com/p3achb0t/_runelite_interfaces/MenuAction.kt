package com.p3achb0t._runelite_interfaces

interface MenuAction {
    fun getAction(): String
    fun getArgument0(): Int
    fun getArgument1(): Int
    fun getArgument2(): Int
    fun getOpcode(): Int
}
