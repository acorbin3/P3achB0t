package com.p3achb0t._runestar_interfaces

interface NPCType : DualNode {
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
    fun get__ao(): Boolean
    fun get__q(): Boolean
    fun get__as(): Int
}
