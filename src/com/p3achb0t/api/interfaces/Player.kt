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
    fun get__ae(): Boolean
    fun get__a(): Int
    fun get__d(): Int
    fun get__e(): Int
    fun get__r(): Int
    fun get__u(): Int
    fun get__x(): Int
    fun get__ag(): TriBool
    fun get__n(): TriBool
    fun get__w(): TriBool
}
