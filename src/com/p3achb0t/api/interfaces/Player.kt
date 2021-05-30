package com.p3achb0t.api.interfaces

interface Player : Actor {
    fun getActions(): Array<String>
    fun getAnimationCycleEnd(): Int
    fun getAnimationCycleStart(): Int
    fun getAppearance(): PlayerAppearance
    fun getCombatLevel(): Int
    fun getHeadIconPk(): Int
    fun getHeadIconPrayer(): Int
    fun getIndex(): Int
    fun getIsHidden(): Boolean
    fun getIsUnanimated(): Boolean
    fun getModel0(): Model
    fun getPlane(): Int
    fun getSkillLevel(): Int
    fun getTeam(): Int
    fun getTileHeight(): Int
    fun getTileHeight2(): Int
    fun getTileX(): Int
    fun getTileY(): Int
    fun getUsername(): Username
    fun get__ap(): Boolean
    fun get__a(): Int
    fun get__c(): Int
    fun get__g(): Int
    fun get__k(): Int
    fun get__l(): Int
    fun get__m(): Int
    fun get__ae(): TriBool
    fun get__i(): TriBool
    fun get__q(): TriBool
}
