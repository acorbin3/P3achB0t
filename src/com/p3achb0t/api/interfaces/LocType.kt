package com.p3achb0t.api.interfaces

interface LocType : DualNode {
    fun multiLoc(): LocType
    fun getAmbient(): Int
    fun getAmbientSoundId(): Int
    fun getAnimationId(): Int
    fun getBoolean1(): Boolean
    fun getClipped(): Boolean
    fun getContrast(): Int
    fun getHillChange(): Int
    fun getId(): Int
    fun getInt2(): Int
    fun getInt3(): Int
    fun getInt4(): Int
    fun getInt5(): Int
    fun getInt6(): Int
    fun getInteractType(): Int
    fun getInteractable(): Int
    fun getIsRotated(): Boolean
    fun getIsSolid(): Boolean
    fun getLength(): Int
    fun getLowDetailVisible(): Boolean
    fun getMapIconId(): Int
    fun getMapSceneId(): Int
    fun getModelTypes(): IntArray
    fun getModels(): IntArray
    fun getMulti(): IntArray
    fun getMultiVar(): Int
    fun getMultiVarbit(): Int
    fun getName(): String
    fun getOcclude(): Boolean
    fun getOffsetX(): Int
    fun getOffsetY(): Int
    fun getOffsetZ(): Int
    fun getOp(): Array<String>
    fun getParams(): IterableNodeHashTable
    fun getRecol_d(): ShortArray
    fun getRecol_s(): ShortArray
    fun getResizeX(): Int
    fun getResizeY(): Int
    fun getResizeZ(): Int
    fun getRetex_d(): ShortArray
    fun getRetex_s(): ShortArray
    fun getSharelight(): Boolean
    fun getWidth(): Int
    fun get__au(): Boolean
    fun get__aa(): IntArray
}
