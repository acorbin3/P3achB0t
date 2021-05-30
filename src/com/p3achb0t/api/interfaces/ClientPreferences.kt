package com.p3achb0t.api.interfaces

interface ClientPreferences {
    fun getHideUsername(): Boolean
    fun getParameters(): Any
    fun getRememberedUsername(): String
    fun getRoofsHidden(): Boolean
    fun getTitleMusicDisabled(): Boolean
    fun getWindowMode(): Int
    fun get__b(): Double
    fun get__d(): Int
    fun get__s(): Int
    fun get__u(): Int
}
