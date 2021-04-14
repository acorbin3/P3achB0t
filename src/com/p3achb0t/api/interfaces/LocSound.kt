package com.p3achb0t.api.interfaces

interface LocSound : Node {
    fun getObj(): LocType
    fun getSoundEffectId(): Int
    fun getSoundEffectIds(): IntArray
    fun getStream1(): RawPcmStream
    fun getStream2(): RawPcmStream
    fun get__b(): Int
    fun get__d(): Int
    fun get__e(): Int
    fun get__k(): Int
    fun get__l(): Int
    fun get__n(): Int
    fun get__o(): Int
    fun get__p(): Int
    fun get__u(): Int
}
