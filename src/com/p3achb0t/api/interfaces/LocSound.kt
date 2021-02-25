package com.p3achb0t.api.interfaces

interface LocSound : Node {
    fun getObj(): LocType
    fun getSoundEffectId(): Int
    fun getSoundEffectIds(): IntArray
    fun getStream1(): RawPcmStream
    fun getStream2(): RawPcmStream
    fun get__a(): Int
    fun get__c(): Int
    fun get__d(): Int
    fun get__h(): Int
    fun get__l(): Int
    fun get__s(): Int
    fun get__v(): Int
    fun get__y(): Int
    fun get__z(): Int
}
