package com.p3achb0t._runelite_interfaces

interface SpotAnimationDefinition : DualNode {
    fun getArchive(): Int
    fun getHeightScale(): Int
    fun getId(): Int
    fun getOrientation(): Int
    fun getRecolorFrom(): ShortArray
    fun getRecolorTo(): ShortArray
    fun getRetextureFrom(): ShortArray
    fun getRetextureTo(): ShortArray
    fun getSequence(): Int
    fun getWidthScale(): Int
    fun get__a(): Int
    fun get__z(): Int
}
