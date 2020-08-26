package com.p3achb0t.api.interfaces

interface SeqType : DualNode {
    fun getFrameCount(): Int
    fun getFrameIds(): IntArray
    fun getFrameIds2(): IntArray
    fun getFrameLengths(): IntArray
    fun getLefthand(): Int
    fun getRighthand(): Int
    fun get__n(): IntArray
    fun get__d(): Boolean
    fun get__c(): Int
    fun get__f(): Int
    fun get__h(): Int
    fun get__v(): Int
    fun get__y(): Int
    fun get__g(): IntArray
}
