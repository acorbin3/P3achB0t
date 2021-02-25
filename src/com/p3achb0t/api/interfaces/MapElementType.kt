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
    fun get__r(): ByteArray
    fun get__f(): Int
    fun get__g(): Int
    fun get__u(): Int
    fun get__x(): Int
    fun get__i(): IntArray
    fun get__m(): IntArray
    fun get__a(): Boolean
    fun get__b(): Boolean
    fun get__y(): Int
}
