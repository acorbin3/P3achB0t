package com.p3achb0t._runelite_interfaces

interface SpotAnimationDefinition : DualNode {
    fun getArchive(): Int
    fun getHeightScale(): Int
    fun getId(): Int
    fun getOrientation(): Int
    fun getRecolorFrom(): Array<Short>
    fun getRecolorTo(): Array<Short>
    fun getRetextureFrom(): Array<Short>
    fun getRetextureTo(): Array<Short>
    fun getSequence(): Int
    fun getWidthScale(): Int
    fun get__a(): Int
    fun get__z(): Int
}
