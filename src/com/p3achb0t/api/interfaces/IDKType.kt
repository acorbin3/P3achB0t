package com.p3achb0t.api.interfaces

interface IDKType : DualNode {
    fun getBodyPart(): Int
    fun getHead(): IntArray
    fun getModels(): IntArray
    fun getRecol_d(): ShortArray
    fun getRecol_s(): ShortArray
    fun getRetex_d(): ShortArray
    fun getRetex_s(): ShortArray
    fun get__e(): Boolean
}
