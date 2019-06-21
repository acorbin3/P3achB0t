package com.p3achb0t._runelite_interfaces

interface ScriptFrame {
    fun getIntLocals(): Array<Int>
    fun getPc(): Int
    fun getScript(): Script
    fun getStringLocals(): Array<String>
}
