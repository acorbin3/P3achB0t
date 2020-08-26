package com.p3achb0t.api.interfaces

interface LocSound : Node {
    fun getObj(): LocType
    fun getSoundEffectId(): Int
    fun getSoundEffectIds(): IntArray
    fun getStream1(): RawPcmStream
    fun getStream2(): RawPcmStream
    fun get__h(): Int
    fun get__i(): Int
    fun get__n(): Int
    fun get__o(): Int
    fun get__s(): Int
    fun get__t(): Int
    fun get__w(): Int
    fun get__x(): Int
    fun get__z(): Int
}
