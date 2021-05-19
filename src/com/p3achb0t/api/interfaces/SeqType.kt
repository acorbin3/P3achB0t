package com.p3achb0t.api.interfaces

interface SeqType : DualNode {
    fun getFrameCount(): Int
    fun getFrameIds(): IntArray
    fun getFrameIds2(): IntArray
    fun getFrameLengths(): IntArray
    fun getLefthand(): Int
    fun getRighthand(): Int
    fun get__i(): IntArray
    fun get__x(): Boolean
    fun get__a(): Int
    fun get__d(): Int
    fun get__e(): Int
    fun get__k(): Int
    fun get__u(): Int
    fun get__b(): IntArray
}
