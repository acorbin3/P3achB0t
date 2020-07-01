package com.p3achb0t.api.interfaces

interface SeqType : DualNode {
    fun getFrameCount(): Int
    fun getFrameIds(): IntArray
    fun getFrameIds2(): IntArray
    fun getFrameLengths(): IntArray
    fun getLefthand(): Int
    fun getRighthand(): Int
    fun get__w(): IntArray
    fun get__y(): Boolean
    fun get__c(): Int
    fun get__d(): Int
    fun get__r(): Int
    fun get__s(): Int
    fun get__v(): Int
    fun get__a(): IntArray
}
