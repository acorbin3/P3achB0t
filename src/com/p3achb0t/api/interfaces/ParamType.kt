package com.p3achb0t.api.interfaces

interface ParamType : DualNode {
    fun getAutodisable(): Boolean
    fun getDefaultint(): Int
    fun getDefaultstr(): String
    fun getType(): Char
}
