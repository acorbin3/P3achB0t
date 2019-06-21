package com.p3achb0t._runelite_interfaces

interface KitDefinition : DualNode {
    fun getArchives(): Array<Int>
    fun getRecolorFrom(): Array<Short>
    fun getRecolorTo(): Array<Short>
    fun getRetextureFrom(): Array<Short>
    fun getRetextureTo(): Array<Short>
    fun get__u(): Array<Int>
    fun get__k(): Boolean
    fun get__o(): Int
}
