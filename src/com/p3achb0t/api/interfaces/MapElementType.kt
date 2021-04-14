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
    fun get__w(): ByteArray
    fun get__q(): Int
    fun get__t(): Int
    fun get__v(): Int
    fun get__x(): Int
    fun get__a(): IntArray
    fun get__r(): IntArray
    fun get__d(): Boolean
    fun get__m(): Boolean
    fun get__b(): Int
}
