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
    fun get__o(): ByteArray
    fun get__a(): Int
    fun get__b(): Int
    fun get__r(): Int
    fun get__y(): Int
    fun get__f(): IntArray
    fun get__s(): IntArray
    fun get__e(): Boolean
    fun get__m(): Boolean
    fun get__t(): Int
}
