package com.p3achb0t.api.interfaces

interface NPCType : DualNode {
    fun transform(): NPCType
    fun getAmbient(): Int
    fun getCombatLevel(): Int
    fun getContrast(): Int
    fun getDrawMapDot(): Boolean
    fun getHead(): IntArray
    fun getHeadIconPrayer(): Int
    fun getId(): Int
    fun getIsFollower(): Boolean
    fun getIsInteractable(): Boolean
    fun getModels(): IntArray
    fun getName(): String
    fun getOp(): Array<String>
    fun getParams(): IterableNodeHashTable
    fun getReadyanim(): Int
    fun getRecol_d(): ShortArray
    fun getRecol_s(): ShortArray
    fun getResizeh(): Int
    fun getResizev(): Int
    fun getRetex_d(): ShortArray
    fun getRetex_s(): ShortArray
    fun getSize(): Int
    fun getTransformVarbit(): Int
    fun getTransformVarp(): Int
    fun getTransforms(): IntArray
    fun getTurnleftanim(): Int
    fun getTurnrightanim(): Int
    fun getWalkanim(): Int
    fun getWalkbackanim(): Int
    fun getWalkleftanim(): Int
    fun getWalkrightanim(): Int
    fun get__ax(): Boolean
    fun get__i(): Boolean
    fun get__al(): Int
}
