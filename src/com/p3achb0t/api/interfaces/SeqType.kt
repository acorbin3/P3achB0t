package com.p3achb0t.api.interfaces

interface SeqType : DualNode {
    fun getFrameCount(): Int
    fun getFrameIds(): IntArray
    fun getFrameIds2(): IntArray
    fun getFrameLengths(): IntArray
    fun getLefthand(): Int
    fun getRighthand(): Int
    fun get__u(): IntArray
    fun get__e(): Boolean
    fun get__a(): Int
    fun get__b(): Int
    fun get__f(): Int
    fun get__m(): Int
    fun get__y(): Int
    fun get__l(): IntArray
}
