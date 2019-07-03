package com.p3achb0t._runelite_interfaces

interface KitDefinition : DualNode {
    fun getArchives(): IntArray
    fun getRecolorFrom(): ShortArray
    fun getRecolorTo(): ShortArray
    fun getRetextureFrom(): ShortArray
    fun getRetextureTo(): ShortArray
    fun get__u(): IntArray
    fun get__k(): Boolean
    fun get__o(): Int
}
