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
    fun get__t(): ByteArray
    fun get__k(): Int
    fun get__r(): Int
    fun get__s(): Int
    fun get__v(): Int
    fun get__h(): IntArray
    fun get__l(): IntArray
    fun get__p(): Int
}
