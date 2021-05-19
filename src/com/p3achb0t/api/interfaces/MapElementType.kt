package com.p3achb0t.api.interfaces

interface MapElementType : DualNode {
    fun getCategory(): Int
    fun getIop(): Array<String>
    fun getLabel(): String
    fun getLabelcolor(): Int
    fun getLabelsize(): Int
    fun getOpbase(): String
    fun getSprite1(): Int
    fun getSprite2(): Int
    fun get__y(): ByteArray
    fun get__d(): Int
    fun get__e(): Int
    fun get__m(): Int
    fun get__u(): Int
    fun get__a(): IntArray
    fun get__s(): IntArray
    fun get__k(): Boolean
    fun get__x(): Boolean
    fun get__l(): Int
}
