package com.p3achb0t._runelite_interfaces

interface ClientPreferences {
    fun getHideUsername(): Boolean
    fun getParameters(): Any
    fun getRememberedUsername(): String
    fun getRoofsHidden(): Boolean
    fun getTitleMusicDisabled(): Boolean
    fun getWindowMode(): Int
}
