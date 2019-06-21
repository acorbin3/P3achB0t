package com.p3achb0t._runelite_interfaces

interface NpcDefinition : DualNode {
    fun getActions(): Array<String>
    fun getArchives(): Array<Int>
    fun getCombatLevel(): Int
    fun getDrawMapDot(): Boolean
    fun getHeadIconPrayer(): Int
    fun getHeightScale(): Int
    fun getId(): Int
    fun getIdleSequence(): Int
    fun getIsFollower(): Boolean
    fun getIsInteractable(): Boolean
    fun getName(): String
    fun getParams(): IterableNodeHashTable
    fun getRecolorFrom(): Array<Short>
    fun getRecolorTo(): Array<Short>
    fun getRetextureFrom(): Array<Short>
    fun getRetextureTo(): Array<Short>
    fun getSize(): Int
    fun getTransformVarbit(): Int
    fun getTransformVarp(): Int
    fun getTransforms(): Array<Int>
    fun getTurnLeftSequence(): Int
    fun getTurnRightSequence(): Int
    fun getWalkSequence(): Int
    fun getWalkTurnLeftSequence(): Int
    fun getWalkTurnRightSequence(): Int
    fun getWalkTurnSequence(): Int
    fun getWidthScale(): Int
    fun get__ag(): Int
    fun get__aq(): Int
    fun get__e(): Array<Int>
    fun get__ak(): Boolean
    fun get__v(): Boolean
    fun get__av(): Int
}
