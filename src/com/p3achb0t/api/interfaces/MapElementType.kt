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
    fun get__h(): ByteArray
    fun get__a(): Int
    fun get__k(): Int
    fun get__m(): Int
    fun get__x(): Int
    fun get__g(): IntArray
    fun get__t(): IntArray
    fun get__l(): Boolean
    fun get__o(): Boolean
    fun get__p(): Int
}
