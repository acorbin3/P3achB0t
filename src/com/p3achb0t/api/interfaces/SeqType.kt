package com.p3achb0t.api.interfaces

interface SeqType : DualNode {
    fun getFrameCount(): Int
    fun getFrameIds(): IntArray
    fun getFrameIds2(): IntArray
    fun getFrameLengths(): IntArray
    fun getLefthand(): Int
    fun getRighthand(): Int
    fun get__e(): IntArray
    fun get__o(): Boolean
    fun get__j(): Int
    fun get__n(): Int
    fun get__r(): Int
    fun get__s(): Int
    fun get__y(): Int
    fun get__u(): IntArray
}
