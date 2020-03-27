package com.p3achb0t._runestar_interfaces

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
    fun get__ae(): Boolean
    fun get__a(): Int
    fun get__b(): Int
    fun get__g(): Int
    fun get__i(): Int
    fun get__p(): Int
    fun get__u(): Int
}
