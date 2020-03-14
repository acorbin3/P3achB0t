package com.p3achb0t.api.interfaces

interface NetFileRequest : DualNode {
    fun getArchive(): Archive
    fun getCrc(): Int
    fun getPadding(): Byte
}
