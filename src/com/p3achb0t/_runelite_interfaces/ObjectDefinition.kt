package com.p3achb0t._runelite_interfaces

interface ObjectDefinition : DualNode {
    fun getActions(): Array<String>
    fun getAmbient(): Int
    fun getAmbientSoundId(): Int
    fun getAnimationId(): Int
    fun getBoolean1(): Boolean
    fun getBoolean2(): Boolean
    fun getClipType(): Int
    fun getClipped(): Boolean
    fun getContrast(): Int
    fun getId(): Int
    fun getInt1(): Int
    fun getInt2(): Int
    fun getInt3(): Int
    fun getInt4(): Int
    fun getInt5(): Int
    fun getInt6(): Int
    fun getInteractType(): Int
    fun getIsRotated(): Boolean
    fun getIsSolid(): Boolean
    fun getMapIconId(): Int
    fun getMapSceneId(): Int
    fun getModelClipped(): Boolean
    fun getModelHeight(): Int
    fun getModelSizeX(): Int
    fun getModelSizeY(): Int
    fun getName(): String
    fun getNonFlatShading(): Boolean
    fun getOffsetHeight(): Int
    fun getOffsetX(): Int
    fun getOffsetY(): Int
    fun getParams(): IterableNodeHashTable
    fun getRecolorFrom(): ShortArray
    fun getRecolorTo(): ShortArray
    fun getRetextureFrom(): ShortArray
    fun getRetextureTo(): ShortArray
    fun getSizeX(): Int
    fun getSizeY(): Int
    fun getTransformConfigId(): Int
    fun getTransformVarbit(): Int
    fun getTransforms(): IntArray
    fun get__d(): IntArray
    fun get__x(): IntArray
    fun get__ax(): IntArray
}
