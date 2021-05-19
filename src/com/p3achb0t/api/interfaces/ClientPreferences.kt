package com.p3achb0t.api.interfaces

interface ClientPreferences {
    fun getHideUsername(): Boolean
    fun getParameters(): Any
    fun getRememberedUsername(): String
    fun getRoofsHidden(): Boolean
    fun getTitleMusicDisabled(): Boolean
    fun getWindowMode(): Int
    fun get__v(): Double
    fun get__b(): Int
    fun get__i(): Int
    fun get__q(): Int
}
