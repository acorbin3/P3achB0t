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
    fun getIsFriend(): TriBool
    fun getIsHidden(): Boolean
    fun getIsInClanChat(): TriBool
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
    fun get__ab(): Boolean
    fun get__a(): Int
    fun get__c(): Int
    fun get__d(): Int
    fun get__f(): Int
    fun get__v(): Int
    fun get__y(): Int
}
