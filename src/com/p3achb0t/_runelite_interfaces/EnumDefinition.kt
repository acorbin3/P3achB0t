package com.p3achb0t._runelite_interfaces

interface EnumDefinition : DualNode {
    fun getDefaultInt(): Int
    fun getDefaultString(): String
    fun getIntVals(): Array<Int>
    fun getKeyType(): Char
    fun getKeys(): Array<Int>
    fun getSize0(): Int
    fun getStringVals(): Array<String>
    fun getValType(): Char
}
