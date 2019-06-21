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
    fun getRecolorFrom(): Array<Short>
    fun getRecolorTo(): Array<Short>
    fun getRetextureFrom(): Array<Short>
    fun getRetextureTo(): Array<Short>
    fun getSizeX(): Int
    fun getSizeY(): Int
    fun getTransformConfigId(): Int
    fun getTransformVarbit(): Int
    fun getTransforms(): Array<Int>
    fun get__d(): Array<Int>
    fun get__x(): Array<Int>
    fun get__ax(): Array<Int>
}
