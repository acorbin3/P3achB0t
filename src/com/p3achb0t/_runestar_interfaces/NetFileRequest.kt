package com.p3achb0t._runestar_interfaces

interface NetFileRequest : DualNode {
    fun getArchive(): Archive
    fun getCrc(): Int
    fun getPadding(): Byte
}
