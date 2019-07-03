package com.p3achb0t._runelite_interfaces

interface EnumDefinition : DualNode {
    fun getDefaultInt(): Int
    fun getDefaultString(): String
    fun getIntVals(): IntArray
    fun getKeyType(): Char
    fun getKeys(): IntArray
    fun getSize0(): Int
    fun getStringVals(): Array<String>
    fun getValType(): Char
}
