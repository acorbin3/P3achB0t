package com.p3achb0t._runelite_interfaces

interface ObjectSound : Node {
    fun getObj(): ObjectDefinition
    fun getSoundEffectId(): Int
    fun getSoundEffectIds(): IntArray
    fun getStream1(): RawPcmStream
    fun getStream2(): RawPcmStream
    fun get__d(): Int
    fun get__f(): Int
    fun get__g(): Int
    fun get__n(): Int
    fun get__o(): Int
    fun get__q(): Int
    fun get__u(): Int
    fun get__w(): Int
    fun get__x(): Int
}
