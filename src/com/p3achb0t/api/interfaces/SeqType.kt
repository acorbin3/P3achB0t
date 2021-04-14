package com.p3achb0t.api.interfaces

interface SeqType : DualNode {
    fun getFrameCount(): Int
    fun getFrameIds(): IntArray
    fun getFrameIds2(): IntArray
    fun getFrameLengths(): IntArray
    fun getLefthand(): Int
    fun getRighthand(): Int
    fun get__l(): IntArray
    fun get__m(): Boolean
    fun get__d(): Int
    fun get__q(): Int
    fun get__r(): Int
    fun get__t(): Int
    fun get__v(): Int
    fun get__h(): IntArray
}
