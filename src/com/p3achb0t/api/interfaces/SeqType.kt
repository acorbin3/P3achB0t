package com.p3achb0t.api.interfaces

interface SeqType : DualNode {
    fun getFrameCount(): Int
    fun getFrameIds(): IntArray
    fun getFrameIds2(): IntArray
    fun getFrameLengths(): IntArray
    fun getLefthand(): Int
    fun getRighthand(): Int
    fun get__s(): IntArray
    fun get__b(): Boolean
    fun get__a(): Int
    fun get__f(): Int
    fun get__g(): Int
    fun get__i(): Int
    fun get__x(): Int
    fun get__q(): IntArray
}
