package com.p3achb0t._runestar_interfaces

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
    fun get__j(): Int
    fun get__l(): Int
    fun get__s(): Int
    fun get__t(): Int
    fun get__k(): IntArray
    fun get__z(): IntArray
    fun get__p(): Int
}
