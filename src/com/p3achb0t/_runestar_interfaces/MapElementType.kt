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
    fun get__m(): ByteArray
    fun get__a(): Int
    fun get__c(): Int
    fun get__n(): Int
    fun get__r(): Int
    fun get__e(): IntArray
    fun get__t(): IntArray
    fun get__z(): Int
}
