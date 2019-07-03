package com.p3achb0t._runelite_interfaces

interface ScriptFrame {
    fun getIntLocals(): IntArray
    fun getPc(): Int
    fun getScript(): Script
    fun getStringLocals(): Array<String>
}
