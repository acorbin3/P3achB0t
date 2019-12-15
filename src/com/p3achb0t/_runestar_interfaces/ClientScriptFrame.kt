package com.p3achb0t._runestar_interfaces

interface ClientScriptFrame {
    fun getIntLocals(): IntArray
    fun getPc(): Int
    fun getScript(): ClientScript
    fun getStringLocals(): Array<String>
}
