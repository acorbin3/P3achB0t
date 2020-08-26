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
    fun get__u(): ByteArray
    fun get__c(): Int
    fun get__f(): Int
    fun get__q(): Int
    fun get__y(): Int
    fun get__a(): IntArray
    fun get__r(): IntArray
    fun get__i(): Int
}
